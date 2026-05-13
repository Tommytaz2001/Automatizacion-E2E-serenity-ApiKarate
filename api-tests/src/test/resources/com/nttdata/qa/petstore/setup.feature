@ignore
Feature: Setup - Create a pet once and return its generated ID

  Scenario: POST /pet and expose the created ID
    * url 'https://petstore.swagger.io/v2'
    * configure headers = { 'Content-Type': 'application/json', 'Accept': 'application/json' }
    * def petData = read('classpath:testdata/pet.json')
    * def newPet  = petData.newPet

    Given path '/pet'
    And request newPet
    When method POST
    Then status 200
    And match response.name == newPet.name
    And match response.id == '#number'

    * def petId = response.id
