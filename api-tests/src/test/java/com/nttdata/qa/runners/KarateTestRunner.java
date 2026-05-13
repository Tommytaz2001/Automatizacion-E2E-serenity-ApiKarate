package com.nttdata.qa.runners;

import com.intuit.karate.junit5.Karate;

class KarateTestRunner {

    @Karate.Test
    Karate testPetStore() {
        return Karate.run("classpath:com/nttdata/qa/petstore/petstore.feature")
                     .relativeTo(getClass());
    }
}
