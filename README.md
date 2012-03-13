# clj-obt-service

Since [clj-obt](https://github.com/ogrim/clj-obt) only support [the Oslo-Bergen-Tagger](https://github.com/noklesta/The-Oslo-Bergen-Tagger) on the Linux platform, this is a simple project to expose the functionality as a web service. Access to a functional installation of the Oslo-Bergen-Tagger is a requirement, follow the directions to install it from here: <https://github.com/noklesta/The-Oslo-Bergen-Tagger>


## Setup

You host the server directly from Leiningen:

	lein run 8085 /home/ogrim/bin/The-Oslo-Bergen-Tagger

Or you can use the standalone jar, requiring only Java:

	java -jar clj-obt-service-0.0.3-standalone.jar 8085 /home/ogrim/bin/The-Oslo-Bergen-Tagger

You will of course supply your own port number and path to the Oslo-Bergen-Tagger. It's recommended to use one of these methods in combination with GNU Screen, or similar.

## Usage

Tag text by sending POST with data parameter to either /text or /json

	http://localhost:8085/text/

        data=Dette skal tagges.


	http://localhost:8085/json/

        data=Dette skal tagges.


Tagging of multiple texts at once is also supported. This can sometimes help speed up the tagging process.

	http://localhost:8085/text/

        data=["Tag denne teksten." "Dette skal og tagges."]


	http://localhost:8085/json/

        data=["Tag denne teksten." "Dette skal og tagges."]



## License

Copyright (C) 2012 Aleksander Skjæveland Larsen

Distributed under the Eclipse Public License, the same as Clojure.
