# statsbudgettet.dk 

StatsBudgettet.dk shows you how the Danish government spends your money.

## Development

git clone this project locally.

Download idea intelliJ community edition

Download notejs from https://nodejs.org/en/download/

To install dependencies you will need to install npm and then run:
    
```shell
npm install
```

Then you will need to install by executing:

```shell
npm install -g bower
```

...and run:

```shell
bower install
```

install grunt by exec: 

npm install -g grunt-cli

then:
```shell
npm install ruby
npm install sass
npm install --save-dev grunt-sass
```

Possibly also these:

```shell
(npm install node-sass)
(npm install ruby --save)
(npm install ruby-sass)
(should be no reason to exec: gem install sass)
```

Finally to run a server locally, you can run:

```shell
grunt serve
```

open a browser: `http://localhost:9000/`

Make a deployable version, run: **grunt deploy**

------------------

The budget generator is a java module added to this project - **JavaBudgetFetcherAndParser**.

This project gets its data from: http://www.oes-cs.dk/olapdatabase/finanslov/index.cgi (
FINANSMINISTERIETS FINANSLOVSDATABASE).

It generates data from 5 levels: Paragraf, Hovedområde, Aktivitetsområde, Hovedkonto, Underkonto.

Choose "vælg struktur", add the 5 levels and choose: "Alle niveauer på én gang".

You should NOT use the "download", which will download a csv file (eg. 66E290.csv). You need the anmærkninger and other stuff that is not included in that document.

This mean you have to use the html source file output...

This file is the input to the parsing module. Save in in resource. Be aware of encoding issues! You may want to choose windows-1252 encoding.


 





--

_Project cloned from TheOpenBudget.org 
(https://github.com/robbiewain/theopenbudget). 
I have currently no intention of making pull requests to the original project, as this will be so much different from theirs._
