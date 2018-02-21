# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

##  2018-01-20
### Added
- Added new API for optional deleting service price config by day
- Added new API for deleting organization time-sheet config by day


##  2018-01-19
### Added
- Added new API for retrieving last message in conversation - `getLastMessageByConversation`

### Changed
- Change `request body` to `request param` with name `message` in `postMessageToOrganization`.

### Removed
- field `messages` was removed from model `ConversationSummary`






