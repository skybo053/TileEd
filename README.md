# Usage

This Tile Editor application can read in, and write out, JSON files for use by 2D games:  Map file is of the form:

{
  "map":
  {
    "rows": <How many rows>,
    "cols": <How many columns>
  },
  "tiles":
  [
    {
      "tile":
      {
        "row": <Which row this tile is in>,
        "col": <Which column this tile is in>,
        "image-name": <Name of your image>,
        "solid": <true or false>,
        "events": <An array of any events associated with this tile, i.e. moves character position, random encounter, hidden item, etc.>
      }
    }, 
    ...more tile objects
