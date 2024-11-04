# Localize
Utility to convert csv data into android, ios or web string resource formats.

## Usage
- Prepare a csv file with 1st column as key, and subsequent columns as locales e.g.

comment   |untranslatable| key           | en           | es  | it  |
----------|--------------| ------------- |------------- | ----- | ----- |
comment_1 |false         | welcome_text  | welcome      | bienvenidos | benvenuto |
comment_2 |true          | bye_text      | goodbye      |   adiós | arrivederci |

- Go to app folder, run command:
```
./gradlew run --args="--file=src/main/resources/test.csv"
```

- Converted string resources will be in exported folder based on platform:
  - Android:
    ```
    exported
    ├── values-en
    │   ├── strings.xml
    ├── values-de
    │   ├── strings.xml
    ├── values-vi
    │   ├── strings.xml
    ```
  - iOS:
    

## Code structure
You can use this project as a starting point for further customization to meet your need.
- `Record.kt`: model class to hold row data
- `Reader.kt`: Reads data from `.csv` file and returns a 2d array.
- `Parser.kt`: Gets 2d array data as input and returns a list of `Record` object
- `Writer.kt`: Writes to file for each `Record` entry, based on format for a specific platform.

## References:
Inspired by these very good open sources:
- https://github.com/talhahasanzia/string-resource-utility
- https://pypi.org/project/mobile-strings-converter/0.1.0/
- https://github.com/rogermolas/csv-localizer