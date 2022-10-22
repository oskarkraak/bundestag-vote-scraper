# Bundestag Vote Scraper
A web scraper that reads data about the recorded votes from the [website of the German parliament](https://www.bundestag.de/abstimmung).

The voting results are provided by the website as .xlsx files, which will be concatenated. 
The output is a single .xlsx file containing all voting results where an .xlsx file has been provided (which is not the case for older votings).

The output file can then be used to analyse the data.

## Usage

### Download
Download the latest release.

### Run
In the following I will refer to the downloaded jar file as `scraper.jar`. 
You can either rename yours to that or replace it with your file name.

To run the program, `java -jar scraper.jar` is sufficient.
This will save a file `BundestagVotes.xlsx` in your working directory.

To get help, run: `java -jar scraper.jar -h`

If you want to change the save location, use the `-path` argument (`java -jar scraper.jar -path="[Path]"`).
Just replace [Path] with your actual file path.

If you want to change the file name, use the `-name` argument (`java -jar scraper.jar -path="[Name]"`). Just
replace [Name] with your actual file name.

Whilst running, the program iterates over all the votes on the website. To output the progress, use the `-v`
argument (`java -jar scraper.jar -v`). This will print every vote with its date and description from the website as it
is downloaded.

These arguments can be used in any combination, for example:
`java -jar scraper.jar -path="C:\Users\Public\Documents\" -name="votes.xlsx" -v`

### Limitations

Since the website is not an official API (as there is no official API), it will block you after some number of requests.
If this happens, switching your location using a VPN will resolve the issue.

## Outlook

The result could be improved by adding the dates to the output.

Also, a database would be a good idea. It would allow me to add data about the politicians and to create an API.

Fortunately, [abgeordnetenwatch.de](https://www.abgeordnetenwatch.de/api/) provides an API that contains all this info.
It is far easier to obtain the data from there as it does not have the limitations the Bundestag website has.

Therefore, I will not continue working on this project.