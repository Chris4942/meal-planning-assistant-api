# Models

## Meal Request Object
This will be sent from the front end to the back end to request the kind of meal desired.

```json
{
  "day": "Monday",
  "meal": "Lunch",
  "requiredAttributes": [
    "ovenless",
    "high-protein",
    "vegan",
    ...
  ]
}
```

The request will be lists of these objects for each meal desired. I expect that the front end will use some kind of smart programming to make this object simple to construct for the user (especially in the case where the user wants something like "Ovenless" for every meal of every day).

# Recipe Object
The backend will respond with recipes that will look like this:
```json
{
  "id": "R_<randomStringOfAlphaNumericaChars>"
  "day": "Monday",
  "meal": "Lunch",
  "recipe": "A String that contains the recipe. Note: This may be changed later so that PDFs, or other media types can be included."
}
```
