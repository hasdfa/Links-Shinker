package com.vadim.hasdfa.hrefshrinker.model

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.vadim.hasdfa.hrefshrinker.HrefShrinkerApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests

/**
 * Created by rakshavadim on 20.10.2017.
 */
//@TestExecutionListeners(DbUnitTestExecutionListener::class)
@SpringBootApplication(scanBasePackageClasses = arrayOf(HrefShrinkerApplication::class))
@DirtiesContext
abstract class AbstractRepositoryTest //: AbstractTransactionalJUnit4SpringContextTests()