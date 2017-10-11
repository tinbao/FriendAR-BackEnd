# Nitrogen Backend

_A REST API written in Java_

[https://api.friendar.tk/](https://api.friendar.tk/)

For [COMP30022](https://handbook.unimelb.edu.au/2017/subjects/comp30022/)

[![Build Status](https://travis-ci.com/COMP30022/Nitrogen-BackEnd.svg?token=p8yLcFuVj6kMWC4pZF7s&branch=master)](https://travis-ci.com/COMP30022/Nitrogen-BackEnd)


# Pre-requirements and Install

This project is built and run using `docker` and `docker-compose`.

Docker contains and downloads all the pre-requirements. Once docker is installed, please follow
the instructions below to run the backend.

These can both be downloaded from [here](https://docker.com). 

Once downloaded the project can be built and run by running `docker-compose up --build`.
If you would like to run it with `db` access add: `-f docker-compose.yml -f docker-compose.dev.yml` ie `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up --build`.
If you would like to run just the database, this can be done by adding `db` ie `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up db`.


# Tests

During build our tests are run.
They can also be run on demand by running `./tests.sh`. Note: the tests also running in a docker container.

# Development Workflow

This repo uses [git flow](http://nvie.com/posts/a-successful-git-branching-model/).

This means, all new features are done on a new branch and then when ready pull requested into `master`. 
Note: for a branch to be successfully merged into master. It has to pass a code review and the tests on travis.

# Deployment instructions
For deployment, it is deployed through continuous deployment Travis once they are commited to GitHub, pass the tests, and are merged with master.

# License

All rights reserved.
