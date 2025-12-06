Feature: Set of tests for user api

  Background:
    * def baseUrl = 'http://localhost:9000/api/v1'
    * def api = call read('user-config.js') baseUrl

  @users
  Scenario: User actions
    Given url api.urls.users
    And request api.request.createUser
    When method post
    Then status 201
    * print response
    * match response contains api.userExpected

    Given path response.id
    When method delete
    Then status 200

    Given url api.urls.users
    When method get
    Then status 200
    * print response
    * match response contains api.schemas.user

    Given path response[0].id
    When method get
    Then status 200
    * print response
    * match response contains api.userExpected

    # update some properties for update the user
    * def user = response
    * user.name = 'peterTest'
    * user.email = 'test@gmail.com'
    * print user

    Given path user.id
    And request user
    When method patch
    Then status 200
    * print response
    * match response contains api.userExpected