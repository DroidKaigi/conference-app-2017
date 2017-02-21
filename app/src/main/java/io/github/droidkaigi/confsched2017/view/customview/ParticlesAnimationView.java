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
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.droidkaigi.confsched2017.R;


public class ParticlesAnimationView extends View {

    @SuppressWarnings("unused")
    private static final String TAG = ParticlesAnimationView.class.getSimpleName();

    // Use a single static Random generator to ensure the randomness. Also save memory.
    private static final Random random = new Random();

    private static final int MAX_HEXAGONS = 40;

    private static final int LINK_HEXAGON_DISTANCE = 600;

    private final Paint paint = new Paint();

    private final List<Particle> particles = new ArrayList<>();

    private final List<Line> lines = new ArrayList<>();

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

            lines.clear();
            for (int i = 0; i < particles.size() - 1; i++) {
                Particle particle = particles.get(i);
                // So there are exactly C(particles.size(), 2) (Mathematical Combination) number of lines, which makes more sense.
                for (int j = i + 1; j < particles.size(); j++) {
                    lines.add(new Line(particle, particles.get(j)));
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lines.clear();
        particles.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).draw(canvas, paint);
        }

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).draw(canvas, paint);
        }
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
            particles.add(new Particle(getWidth(), getHeight(), this));
        }
        return particles;
    }

    /**
     * A Pair of 'Particles' whose centers can be 'linked to each other ... Called 'Line'.
     */
    private static class Line extends Pair<Particle, Particle> {

        private static final int MAX_ALPHA = 172;

        /**
         * Constructor for a Pair.
         *
         * @param first  the first object in the Pair
         * @param second the second object in the pair
         */
        Line(Particle first, Particle second) {
            super(first, second);
        }

        void draw(Canvas canvas, Paint paint) {
            if (!first.shouldBeLinked(LINK_HEXAGON_DISTANCE, second)) {
                return;
            }

            final double distance = Math.sqrt(
                    Math.pow(second.center.x - first.center.x, 2) + Math.pow((second.center.y - first.center.y), 2)
            );
            final int alpha = MAX_ALPHA - (int) Math.floor(distance * MAX_ALPHA / LINK_HEXAGON_DISTANCE);
            paint.setAlpha(alpha);
            canvas.drawLine(first.center.x, first.center.y, second.center.x, second.center.y, paint);
        }
    }

    private static class Particle {

        private static final int MAX_ALPHA = 128;
        private static final float BASE_RADIUS = 100f;

        private float scale;
        private int alpha;
        private float moveSpeed;
        private float flashSpeed;
        private Point center;
        private Point vector;

        final Path path = new Path();

        Particle(int maxWidth, int maxHeight, View view) {
            this(maxWidth, maxHeight, view.getWidth(), view.getHeight());
        }

        Particle(int maxWidth, int maxHeight, int hostWidth, int hostHeight) {
            center = new Point();
            vector = new Point();
            reset(maxWidth, maxHeight);
            center.x = (int) (hostWidth - hostWidth * random.nextFloat());
            center.y = (int) (hostHeight - hostHeight * random.nextFloat());
        }

        boolean shouldBeLinked(int linkedDistance, Particle particle) {
            if (this.equals(particle)) {
                return false;
            }
            // Math.pow(x, 2) and x * x stuff, for your information and my curiosity.
            // ref: http://hg.openjdk.java.net/jdk8u/jdk8u/hotspot/file/5755b2aee8e8/src/share/vm/opto/library_call.cpp#l1799
            final double distance = Math.sqrt(
                    Math.pow(particle.center.x - this.center.x, 2) + Math.pow(particle.center.y - this.center.y, 2)
            );
            return distance < linkedDistance;
        }

        void draw(Canvas canvas, Paint paint) {
            move(canvas.getWidth(), canvas.getHeight());
            paint.setAlpha(alpha);
            createHexagonPathOrUpdate(center.x, center.y, scale);
            canvas.drawPath(this.path, paint);
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

        private void createHexagonPathOrUpdate(float centerX, float centerY, float scale) {
            path.reset();
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
        }

    }

}
