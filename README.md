# WhatsApp Bulk Messaging App

A modern Android application for sending WhatsApp messages with manual confirmation, featuring ad integration and contact management.

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox (2021.3.1) or newer
- JDK 11 or newer
- Android SDK with minimum API level 21 (Android 5.0)
- Android device or emulator running Android 5.0 or higher

### Project Setup in Android Studio

1. **Clone/Download the Project**
   ```bash
   git clone <repository-url>
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to and select the project directory
   - Click "OK" and wait for the project to sync

3. **Configure Gradle**
   - Wait for the automatic Gradle sync to complete
   - If sync fails, click "Try Again" or "Fix Gradle version"
   - Ensure all dependencies are properly downloaded

4. **Set Up Ad Integration**
   - Replace test ad unit IDs in layout files:
     - `activity_main.xml`: Replace `ca-app-pub-3940256099942544/6300978111`
     - `activity_messaging.xml`: Replace `ca-app-pub-3940256099942544/6300978111`
   - Update your AdMob app ID in `AndroidManifest.xml`

### Building and Running

1. **Build the Project**
   - Click Build → Make Project (or press Ctrl+F9/Cmd+F9)
   - Resolve any build errors if they occur

2. **Run on Device/Emulator**
   - Connect an Android device via USB with USB debugging enabled
   - Or set up an Android emulator in AVD Manager
   - Click Run → Run 'app' (or press Shift+F10/Ctrl+R)
   - Select your target device and click OK

### Project Structure

```
app/
├── build.gradle                 # App-level Gradle build file
├── src/main/
    ├── AndroidManifest.xml     # App manifest
    ├── java/com/example/whatsbulkapp/
    │   ├── MainActivity.kt     # Main screen
    │   ├── MessagingActivity.kt# Message composition
    │   └── ContactImporter.kt  # Contact management
    └── res/
        ├── layout/
        │   ├── activity_main.xml       # Main screen layout
        │   └── activity_messaging.xml  # Messaging screen layout
        └── values/
            ├── colors.xml      # Color definitions
            └── styles.xml      # Style definitions
```

## Features

### 1. Main Screen
- Modern Material Design UI
- Legal disclaimer and warnings
- AdMob banner integration
- Navigation to messaging screen

### 2. Messaging Screen
- Message composition
- Delay/throttling settings (0-10 seconds)
- Contact selection
- Manual WhatsApp sending via Intent
- AdMob banner integration

### 3. Contact Management
- Contact list access with permissions
- WhatsApp contact filtering
- Efficient contact data handling

## Permissions

The app requires the following permissions:
- `INTERNET`: For ad functionality
- `ACCESS_NETWORK_STATE`: For ad functionality
- `READ_CONTACTS`: For contact selection

## Important Notes

1. **WhatsApp Policy Compliance**
   - The app uses manual sending to comply with WhatsApp's Terms of Service
   - No automated messaging is implemented
   - Users must confirm each message send

2. **Ad Integration**
   - Test ad unit IDs are included by default
   - Replace with real ad unit IDs before publishing
   - Follow AdMob policies for proper ad placement

3. **Contact Access**
   - Runtime permissions are implemented for contact access
   - Contact selection is optional
   - Contact data is handled securely

## Troubleshooting

1. **Build Issues**
   - Ensure Gradle sync is complete
   - Check Android Studio and plugin versions
   - Verify SDK installation

2. **Runtime Issues**
   - Verify WhatsApp is installed
   - Check required permissions are granted
   - Ensure internet connection for ads

3. **Ad Integration Issues**
   - Verify ad unit IDs
   - Check internet connection
   - Review AdMob console for errors

## Development Guidelines

1. **Code Style**
   - Follow Kotlin coding conventions
   - Use meaningful variable and function names
   - Add comments for complex logic

2. **UI/UX**
   - Follow Material Design guidelines
   - Maintain consistent styling
   - Ensure proper error handling and user feedback

3. **Testing**
   - Test on multiple Android versions
   - Verify all features work as expected
   - Check different screen sizes and orientations

## License

[Add your license information here]

## Support

[Add support contact information here]
