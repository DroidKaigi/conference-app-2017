package io.github.droidkaigi.confsched2017.view.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.droidkaigi.confsched2017.R;


public class ParticlesAnimationView extends View {

    private static final String TAG = ParticlesAnimationView.class.getSimpleName();
    private static final int MAX_HEXAGONS = 40;
    private static final int LINK_HEXAGON_DISTANCE = 600;

    private final Paint paint = new Paint();
    private final List<Particle> particles = new ArrayList<>();

    public ParticlesAnimationView(Context context) {
        this(context, null);
    }

    public ParticlesAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParticlesAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.WHITE);
        setGradientBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            particles.clear();
            particles.addAll(createParticles(MAX_HEXAGONS));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Stream.of(particles).forEach(particle -> particle.draw(canvas, paint));
        Stream.of(createLines(particles)).forEach(line -> line.draw(canvas, paint));
    }

    private void setGradientBackground() {
        final ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, height, width, 0,
                        new int[]{
                                ContextCompat.getColor(getContext(), R.color.dark_light_green),
                                (ContextCompat.getColor(getContext(), R.color.dark_light_green)
                                        + ContextCompat.getColor(getContext(), R.color.dark_purple)) / 2,
                                ContextCompat.getColor(getContext(), R.color.dark_purple),
                                (ContextCompat.getColor(getContext(), R.color.dark_purple)
                                        + ContextCompat.getColor(getContext(), R.color.dark_pink)) / 2,
                                ContextCompat.getColor(getContext(), R.color.dark_pink)
                        },
                        new float[]{
                                0.0f, 0.15f, 0.5f, 0.85f, 1.0f
                        },
                        Shader.TileMode.CLAMP);
            }
        };
        final PaintDrawable backgroundPaint = new PaintDrawable();
        backgroundPaint.setShape(new RectShape());
        backgroundPaint.setShaderFactory(shaderFactory);
        setBackground(backgroundPaint);
    }

    private List<Particle> createParticles(int count) {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(getWidth(), getHeight()));
        }
        return particles;
    }

    private List<Line> createLines(List<Particle> particles) {
        final List<Line> lines = new ArrayList<>();
        Stream.of(particles)
                .forEach(particle -> Stream.of(particles)
                        .filter(targetParticle ->
                                particle.isShouldLinked(LINK_HEXAGON_DISTANCE, targetParticle)
                        )
                        .map(linkParticle -> new Line(new float[]{
                                particle.center.x,
                                particle.center.y,
                                linkParticle.center.x,
                                linkParticle.center.y})
                        )
                        .forEach(line -> {
                            if (!lines.contains(line)) lines.add(line);
                        })
                );
        return lines;
    }

    private class Line {

        private static final int MAX_ALPHA = 172;
        private float[] point;

        public Line(float[] point) {
            this.point = point;
        }

        public void draw(Canvas canvas, Paint paint) {
            final double distance = Math.floor(Math.sqrt(
                    (point[2] - point[0]) * (point[2] - point[0])
                            + (point[3] - point[1]) * (point[3] - point[1])
            ));
            final int alpha = MAX_ALPHA - (int) Math.floor(distance * MAX_ALPHA / LINK_HEXAGON_DISTANCE);
            paint.setAlpha(alpha);
            canvas.drawLines(point, paint);
        }

        @Override
        public boolean equals(Object obj) {
            final float[] targetPoint = ((Line) obj).point;
            // return true if same point or reverse point
            if (point[0] == targetPoint[0] && point[1] == targetPoint[1]
                    && point[2] == targetPoint[2] && point[3] == targetPoint[3]) {
                return true;
            } else if (point[0] == targetPoint[2] && point[1] == targetPoint[3]
                    && point[2] == targetPoint[0] && point[3] == targetPoint[1]) {
                return true;
            } else {
                return super.equals(obj);
            }
        }

    }

    private class Particle {

        private static final int MAX_ALPHA = 128;
        private static final float BASE_RADIUS = 100f;

        private float scale;
        private int alpha;
        private float moveSpeed;
        private float flashSpeed;
        private Point center;
        private Point vector;

        public Particle(int maxWidth, int maxHeight) {
            center = new Point();
            vector = new Point();
            reset(maxWidth, maxHeight);
            Random random = new Random();
            center.x = (int) (getWidth() - getWidth() * random.nextFloat());
            center.y = (int) (getHeight() - getHeight() * random.nextFloat());
        }

        public boolean isShouldLinked(int linkedDistance, Particle particle) {
            if (this.equals(particle)) return false;
            final double distance = Math.sqrt(
                    Math.pow(particle.center.x - this.center.x, 2) + Math.pow(particle.center.y - this.center.y, 2)
            );
            return distance < linkedDistance;
        }

        public void draw(Canvas canvas, Paint paint) {
            move(canvas.getWidth(), canvas.getHeight());
            paint.setAlpha(alpha);
            canvas.drawPath(getHexagonPath(center.x, center.y, scale), paint);
        }

        private void move(int maxWidth, int maxHeight) {
            alpha += flashSpeed;
            if (alpha < 0) {
                alpha = 0;
                flashSpeed = Math.abs(flashSpeed);
            } else if (MAX_ALPHA < alpha) {
                alpha = MAX_ALPHA;
                flashSpeed = -Math.abs(flashSpeed);
            }

            center.x += vector.x * moveSpeed;
            center.y += vector.y * moveSpeed;
            final int radius = (int) (BASE_RADIUS * scale);
            if ((center.x < -radius || maxWidth + radius < center.x)
                    || (center.y < -radius || maxHeight + radius < center.y)) {
                reset(maxWidth, maxHeight);
            }
        }

        private void reset(int maxWidth, int maxHeight) {
            Random random = new Random();
            scale = random.nextFloat() + random.nextFloat();
            alpha = random.nextInt(MAX_ALPHA + 1);
            moveSpeed = random.nextFloat() + random.nextFloat() + 0.5f;
            flashSpeed = random.nextInt(8) + 1f;

            // point on the edge of the screen
            final int radius = (int) (BASE_RADIUS * scale);
            if (random.nextBoolean()) {
                center.x = (int) (maxWidth - maxWidth * random.nextFloat());
                center.y = random.nextBoolean() ? -radius : maxHeight + radius;
            } else {
                center.x = random.nextBoolean() ? -radius : maxWidth + radius;
                center.y = (int) (maxHeight - maxHeight * random.nextFloat());
            }

            // move direction
            vector.x = (random.nextInt(5) + 1) * (random.nextBoolean() ? 1 : -1);
            vector.y = (random.nextInt(5) + 1) * (random.nextBoolean() ? 1 : -1);
            if (center.x == 0) {
                vector.x = Math.abs(vector.x);
            } else if (center.x == maxWidth) {
                vector.x = -Math.abs(vector.x);
            }
            if (center.y == 0) {
                vector.y = Math.abs(vector.y);
            } else if (center.y == maxHeight) {
                vector.y = -Math.abs(vector.y);
            }
        }

        private Path getHexagonPath(float centerX, float centerY, float scale) {
            final Path path = new Path();
            final int radius = (int) (BASE_RADIUS * scale);
            for (int i = 0; i < 6; i++) {
                float x = (float) (centerX + radius * (Math.cos(2.0 * i * Math.PI / 6.0 + Math.PI)));
                float y = (float) (centerY - radius * (Math.sin(2.0 * i * Math.PI / 6.0 + Math.PI)));
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();
            return path;
        }

    }

}
