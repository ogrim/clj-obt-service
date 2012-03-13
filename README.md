# clj-obt-service

Since [clj-obt](https://github.com/ogrim/clj-obt) only support [the Oslo-Bergen-Tagger](https://github.com/noklesta/The-Oslo-Bergen-Tagger) on the Linux platform, this is a simple project to expose the functionality as a web service. Access to a functional installation of the Oslo-Bergen-Tagger is a requirement, follow the directions to install it from here: <https://github.com/noklesta/The-Oslo-Bergen-Tagger>


## Setup

You host the server directly from Leiningen:

	lein run 8085 /home/ogrim/bin/The-Oslo-Bergen-Tagger

Or you can use the standalone jar, requiring only Java:

	java -jar clj-obt-service-0.0.3-standalone.jar 8085 /home/ogrim/bin/The-Oslo-Bergen-Tagger

You will of course supply your own port number and path to the Oslo-Bergen-Tagger. It's recommended to use one of these methods in combination with GNU Screen, or similar.

## Usage

Tag text by sending POST requests with data parameter to either /text or /json.

	http://localhost:8085/text/
	http://localhost:8085/json/

You can tag either one string, or multiple strings at once. Tagging multiple strings can sometimes help speed up the tagging process.

    "Dette må tagges"
    "[\"Dette må tagges\" \"Dette skal også tagges!\"]"

An example of calling the service using `clj-http`, first URL-encode the data:

    (clj-http.util/url-encode "Dette må tagges")

    =>

    "Dette+m%C3%A5+tagges"

Send POST request to service:

    (clj-http.client/post "http://localhost:8085/text"
        {:body "data=Dette+m%C3%A5+tagges"
         :content-type "application/x-www-form-urlencoded"
         :as "ISO-8859-1"})

     =>

     {:trace-redirects ["http://localhost:8085/text"], :status 200,
      :headers {"date" "Tue, 13 Mar 2012 12:25:37 GMT", "connection" "close",
      "server" "Jetty(6.1.25)"},
      :body "[{:tags [\"pron\" \"nøyt\" \"ent\" \"pers\" \"3\"], :lemma \"dette\",
        :word \"Dette\", :i 1} {:tags [\"verb\" \"pres\" \"tr6\" \"pa4/til\" \"<aux1/infinitiv>\"],
        :lemma \"måtte\", :word \"må\", :i 2} {:tags [\"verb\" \"pres\" \"inf\" \"pass\" \"tr1\" \"<<<\"],
        :lemma \"tagge\", :word \"tagges\", :i 3}]"}


## License

Copyright (C) 2012 Aleksander Skjæveland Larsen

Distributed under the Eclipse Public License, the same as Clojure.
