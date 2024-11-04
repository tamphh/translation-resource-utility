# Localize
Utility to convert CSV data into Android and iOS string resource formats.

## Usage
Prepare a CSV file with 1st column as `comment`, 2nd one as `untranslatable`, 3rd one as `key`, and subsequent columns as locales e.g.

comment   |untranslatable| key           | en           | es  | it  |
----------|--------------| ------------- |------------- | ----- | ----- |
comment_1 |false         | welcome_text  | welcome      | bienvenidos | benvenuto |
comment_2 |true          | bye_text      | goodbye      |   adiós | arrivederci |

### Go to app folder, run command:
  ```shell
  ./gradlew run --args="-f path/to/your_file.csv -os {platform} -d {delimiter}"
  ```
Delimiter is default to **comma** **`,`**

**Example**:
  ```shell
  // Android
  ./gradlew run --args="-f src/main/resources/sample.csv -os android -d ;"
  
  // iOS
  ./gradlew run --args="-f src/main/resources/test.csv -os ios -d ,"
  
  // same as
  ./gradlew run --args="-f src/main/resources/test.csv -os ios"
  ```

### Converted string resources will be in exported folder based on platform:
- **For Android**:
    ```shell
    ./gradlew run --args="-f src/main/resources/sample.csv -os android -d ;"
    ``` 
  Output result:
    ```markdown
    exported
    ├── values-en
    │   ├── strings.xml
    ├── values-de
    │   ├── strings.xml
    ├── values-vi
    │   ├── strings.xml
    ```
- **For iOS**:
    ```shell
    ./gradlew run --args="-f src/main/resources/test.csv -os ios -d ,"
    ``` 
  Output result:
    ```markdown
    exported
    ├── en.lproj
    │   ├── Localizable.strings
    ├── de.lproj
    │   ├── Localizable.strings
    ├── vi.lproj
    │   ├── Localizable.strings
    ```

## Code structure
You can use this project as a starting point for further customization to meet your need.
- `Record.kt`: Model class to hold row data
- `Reader.kt`: Reads data from `.csv` file and returns a 2d array.
- `Parser.kt`: Gets 2d array data as input and returns a list of `Record` object
- `Writer.kt`: Writes to file for each `Record` entry, based on format for a specific platform.
- `Converter.kt`: Put `Record.kt` `Reader.kt` `Parser.kt` `Writer.kt` all together for converting process.

## TODO
- [ ] Better exception handling
- [ ] Build to command line binary
- [ ] Document