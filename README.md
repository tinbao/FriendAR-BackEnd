# Nitrogen Backend

_A REST API written in Java_

[https://api.friendar.tk/](https://api.friendar.tk/)

For [COMP30022](https://handbook.unimelb.edu.au/2017/subjects/comp30022/)

[![Build Status](https://travis-ci.com/COMP30022/Nitrogen-BackEnd.svg?token=p8yLcFuVj6kMWC4pZF7s&branch=master)](https://travis-ci.com/COMP30022/Nitrogen-BackEnd)


# Install

This project is built and run using `docker` and `docker-compose`.

These can both be downloaded from [here](https://docker.com). 

Once downloaded the projcect and be built and run by running `docker-compose up --build`.


# Tests

During build our tests are run.
They can also be run on demand by running `./tests.sh`. Note: the tests also running in a docker container.

# Development Workflow.

This repo uses [git flow](http://nvie.com/posts/a-successful-git-branching-model/).

This means, all new features are done on a new branch and then when ready pull requested into `master`. 
Note: for a branch to be successfully merged into master. It has to pass a code review and the tests on travis.
