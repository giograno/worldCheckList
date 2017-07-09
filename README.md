# World Check

World Check is a prototype search framework built on the top of ElasticSearch.

### Scenario Usage
Banks and financial organizations uses daily-updated lists with sentitive people that need to be carefully treated by such institution. Such lists contains suspect terrorists, politics, their relatives and so on.

Often, it could happen that a given person could not be uniquely identified. This is due, for instance, to registration errors in bank systems, or to multiple middle names (especially for Arabic people).

This prototype computes a score that indicate how close is a person to the one that is registred in bank systems.
