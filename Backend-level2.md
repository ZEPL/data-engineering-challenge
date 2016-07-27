Challenge for Software Engineer
===============================

To better assess a candidates development skills, we would like to provide the following challenge. You have as much time as you'd like (though we ask that you not spend more than a few hours).

Feel free to email us at dev@nflabs.com if you have any questions about the challenge.

## Submission Instructions
1. First, fork this project on github. You will need to create an account if you don't already have one.
1. Next, complete the project as described below within your fork.
1. Finally, push all of your changes to your fork on github and submit a pull request. You should also email us to let know you have submitted a solution. Make sure to include your github username in your email (so we can match people with pull requests).

If you submit a PR before the due date, we will review and leave comments to give you a chance to improve your codes.

## Project Description
Imagine that your just joined a cool BigData startup wich stores a lot of customer data. You are lucky as they are just starting a new project to analyze all this aswome shiny data. The system consists of many parts and your team leads to develop storing customer data system.

Here's what your system must do:

1. Your system must receive customer data with various json format via http
1. Your system must change some data to "*"
1. Your system must store data into files

If any error occurs while handling data, it returns `fails`

Here's your system's entry point as http server:

* GET /mask
  * Returns comma seperated list of adopted field names
* POST /mask with comma seperated list of field names
  * Returns comma seperated list of adopted field names
* POST /log with json data
  * Returns json data described below
* all others should return http status of 400

Here's input json format of `/log`:

```
{
  "meta": {
    "field_1": "data_1",
    ...
  },
  "data": {
    "field_1": "data_1",
    "field_2": {
      "sub_field_1": "sub_data_1",
      ...
    },
    ...
  },
}
```

Here's output json format of `/log`:

* If success, return 200 with 

  ```
{
    "result": "success"
}
```

* If fails, return 499 with

  ```
{
    "result": "fails",
    "error" : "error_message"
}
```

Your system should be easy to set up and should run on either Linux or Mac OS X. It should not require any for-pay software. It should be well-tested solution and easy-to-extend too, by quickly adding a new customer requirments. It's only allowed by Jetty and JAX-RS as web framework.

## Evaluation
Evaluation of your submission will be based on the following criteria. Additionally, reviewers will attempt to assess your familiarity with libraries (so use them as much as possible). In Java project reviewers will attempt to assess your experience with object-oriented programming based on how you've structured your submission. Any help with reasoning about a run-time perfomace of your application (i.e perf.txt document, describing ti) would be a plus for a candidate.

1. Did your application fulfill the basic requirements?
1. Did you follow the instructions for submission?
1. What is a test coverage for your system
1. How easy is to add more additional point to store data
1. What is the performance of your system
  1. How much logs can your system receive at the same time?