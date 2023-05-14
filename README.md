# GeoParserApp


This is an app to parse an input text to a Json string.


## Features

It is currently supporting 2 types of tag:
- Person mention - starts by "@"
- Http(s) link - "page title; http(s)://pageurl.com"

## Tech

This app contains 2 modules:

- :app - This is main app module contains Android code
- :parser - This is library module contains the parsing logic


## Usage

This app requires **JDK 11+** to run (Can use the embedded JDK from the Android studio)

> Clone the source code from github
```sh
git clone https://github.com/comsuon/GeoParserApp.git
cd GeoParserApp
```

> Build debug apk, the apk is found in `/app/build/outputs/apk/debug`
```sh
./gradlew :app:assembleDebug
```

> to install app to device, please connect device and use this instead
 ```sh
 ./gradlew :app:installDebug
 ```

### Unit-test

```sh
./gradlew :parser:test
```
> test reports can be found in `/parser/build/reports/tests/test/index.html`

Can run test with coverage from Android studio 
### Troubleshooting
1. Please ensure the java -version is 11+
2. Please connect device and set to debug mode before run command :app:installDebug


## License

MIT

