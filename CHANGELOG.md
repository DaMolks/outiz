# Changelog

## [Unreleased]

### Added
- Resources for time tracking and date formatting
- String resources for various adapters and fragments
- Localized strings for delete confirmations and error messages

### Changed
- Replaced `DateTimePickerDialog` with Material Design Date and Time Pickers in `ReportInfoFragment`
- Updated `strings.xml` with comprehensive localization resources

### Fixed
- Resolved unresolved reference errors in multiple fragments and adapters
- Corrected navigation and string formatting issues

## Modification Log

### 2024-01-07 16:45 UTC
- Added `time_format_detailed` resource for TimeEntriesAdapter
- Resource: `%1$d h %2$d min (%3$s)`

### 2024-01-07 16:50 UTC
- Added `site_code_format` and `site_client_format` for SitesAdapter
- Resources: 
  - `Code : %1$s`
  - `Client : %1$s`

### 2024-01-07 17:00 UTC
- Updated `ReportInfoFragment` to use Material Date and Time Pickers
- Replaced Android's `DateTimePickerDialog` with `MaterialDatePicker` and `MaterialTimePicker`

### 2024-01-07 17:15 UTC
- Added resources for SitesFragment and SitesAdapter
- Included delete confirmation and empty view strings
- Added navigation-related string resources

## Contributing
When adding to this changelog, please follow these guidelines:
- Use [Semantic Versioning](https://semver.org/)
- Group changes into categories: Added, Changed, Deprecated, Removed, Fixed, Security
- Include precise datetime and brief, clear descriptions