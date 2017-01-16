GIT_HASH=`git rev-parse --short HEAD`

curl -F "file=@app/build/outputs/apk/app-production-debug.apk" -F "token=${DEPLOY_GATE_API_KEY}" -F "message=https://github.com/DroidKaigi/conference-app-2017/tree/${GIT_HASH} https://circleci.com/gh/DroidKaigi/conference-app-2017/${CIRCLE_BUILD_NUM}" -F "distribution_key=e504e2f8aa85fb76eccd36fb0626b8b998eb09c4" -F "release_note=https://github.com/DroidKaigi/conference-app-2017/tree/${GIT_HASH} https://circleci.com/gh/DroidKaigi/conference-app-2017/${CIRCLE_BUILD_NUM}" https://deploygate.com/api/users/konifar/apps
