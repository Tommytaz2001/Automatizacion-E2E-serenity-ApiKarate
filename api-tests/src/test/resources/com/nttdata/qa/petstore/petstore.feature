@API @PetStore
Feature: PetStore API CRUD Operations
  As an API consumer of the PetStore service
  I want to manage pet records via the REST API
  So that I can verify create, read, update and query operations work correctly

  Background:
    * url baseUrl
    * configure headers = defaultHeaders
    # callonce runs setup.feature exactly once per test session and caches the result.
    # All scenarios in this file share the same petId without repeating the POST.
    * def setup     = callonce read('classpath:com/nttdata/qa/petstore/setup.feature')
    * def createdPetId = setup.petId

  @AddPet
  Scenario: Añadir una mascota a la tienda
    # Verify the pet created during setup is accessible (confirms the POST succeeded)
    Given path '/pet/' + createdPetId
    When method GET
    Then status 200
    And match response.id == createdPetId
    And match response.name == '#string'
    And match response.status == 'available'

  @GetPet
  Scenario: Consultar la mascota ingresada previamente (Búsqueda por ID)
    Given path '/pet/' + createdPetId
    When method GET
    Then status 200
    And match response.id == createdPetId
    And match response.name == '#string'

  @UpdatePet
  Scenario: Actualizar el nombre de la mascota y el estatus a sold
    * def petData = read('classpath:testdata/pet.json')

    * def updatePayload =
      """
      {
        "id": #(createdPetId),
        "category": { "id": 1, "name": "Dogs" },
        "name": #(petData.updatedName),
        "photoUrls": ["https://example.com/photos/buddy.jpg"],
        "tags": [{ "id": 1, "name": "friendly" }],
        "status": #(petData.updatedStatus)
      }
      """

    Given path '/pet'
    And request updatePayload
    When method PUT
    Then status 200
    And match response.name == petData.updatedName
    And match response.status == 'sold'

  @FindByStatus
  Scenario: Consultar la mascota modificada por estatus (Búsqueda por estatus)
    Given path '/pet/findByStatus'
    And param status = 'sold'
    When method GET
    Then status 200
    And match response == '#array'
    * def soldPets = karate.filter(response, function(pet){ return pet.status == 'sold' })
    * assert soldPets.length > 0
