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

--

_Project cloned from TheOpenBudget.org 
(https://github.com/robbiewain/theopenbudget). 
I have currently no intention of making pull requests to the original project, as this will be so much different from theirs._
