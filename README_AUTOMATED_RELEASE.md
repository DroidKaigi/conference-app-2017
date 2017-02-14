# Automated release

Make releasing easy!

If you have any question, feel free to ask to me (@wakwa3125).

# ATTENTION

**FIRST YOU MUST UPLOAD YOUR APK FILE WITH NON-AUTOMATED FLOW**  
**THIS IS PUBLISHING API RESTRICT**

# Setup

## 1. Install supply(fastlane)

```
gem install fastlane
```

## 2. Create Service Account at GooglePlayConsole.

1. Go to your Google Play Developer Console.
2. Select Settings tab.
3. Select API access tab.
4. Click Create ServiceAccount and follow the dialog.
5. Provide name for the ServiceAccount.
6. Click Select a role and choose Project > **Service Account Actor**
7. **Furnish a new private key checkbox**(It's important!)
8. Select JSON as the key type.
9. Click create.
10. Download the JSON key file as file name **key.json**
11. Back to Google Play Developer Console, and click Done button.
12. Provide access to the new ServiceAccount.
13. Set Release Manager as role of new ServiceAccount.
14. Click add user to close the dialog.

## 3. Prepare for this project.

1. First run `./gradlew setUpRelease`  
This just create required files.

2. Put your key.json file to project root.  
This file has add to .gitignore. So it's safe.

3. Open `deploy2store.sh` and replace `[YOUR_APK_FILE_NAME]` to yours.

4. Second run `./gradlew donwloadMetaData`  
This download all metadata(such as changelogs) from GooglePlayStore and save them to repo.

# Release
## Write changelogs 
- **You can skip this. Because you can wirte changelog at your console.**  
**File name roule is [versioncode].txt**  
Save your changelogs like a following directory.   
This directory is made by run `./gradlew downloadMetaData`

```
└── fastlane
    └── metadata
        └── android
            ├── en-US
            │   └── changelogs
            │       ├── 100000.txt
            │       └── 100100.txt
            └── fr-FR
                └── changelogs
                    └── 100100.txt
                    ...
```

## Run the script
- Just run `./gradlew releaseApp`  
APK will upload to alpha channel.