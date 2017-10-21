package com.vadim.hasdfa.hrefshrinker.ssecurity

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.concurrent.thread
import kotlin.reflect.jvm.kotlinProperty

/**
 * Created by rakshavadim on 21.10.2017.
 */
class CheckObject private constructor() {
    companion object {
        public fun compare(o1: Any, o2: Any): Pair<Boolean, Error?> {
            return compare(o1::class.java, o2::class.java)
        }
        public fun compareAsync(o1: Any, o2: Any, onResult: (Pair<Boolean, Error?>) -> Unit) {
            thread {
                onResult(compare(o1::class.java, o2::class.java))
            }
        }
        public fun compare(o1: Class<*>, o2: Class<*>): Pair<Boolean, Error?> {
            val c1 = o1::class.java
            val c2 = o2::class.java

            if (c1.simpleName != c2.simpleName)
                return Pair(false, Error("Some problems with simpleName"))

            if (c1.canonicalName != c2.canonicalName)
                return Pair(false, Error("Some problems with canonicalName"))

            val checkConstructors: (Array<out Constructor<*>>, Array<out Constructor<*>>) -> Pair<Boolean, Error?> = { constr1, constr2->
                for (cn1 in constr1)
                    for (cn2 in constr1) {
                        try {
                            if (!cn1.isAccessible || !cn2.isAccessible) break

                            val constructor1Parameters = cn1.parameters.map { it.type.newInstance() }
                            val constructor2Parameters = cn2.parameters.map { it.type.newInstance() }

                            if (constructor1Parameters != constructor2Parameters)
                                Pair(false, Error("Some problems with constructors parameters"))

                            val some1 = cn1.newInstance(constructor1Parameters)
                            val some2 = cn1.newInstance(constructor2Parameters)

                            if (some1 != some2)
                                Pair(false, Error("Some problems with componentType"))

                            if (some1.equals(some2) != some2.equals(some1))
                                Pair(false, Error("Some problems with equals methods"))

                            if (some1.hashCode() != some2.hashCode())
                                Pair(false, Error("Some problems with hashCode methods"))

                            if (some1.toString() != some2.toString())
                                Pair(false, Error("Some problems with toString methods"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                Pair(true, null)
            }

            val check1 = checkConstructors(c1.constructors, c2.constructors)
            if (!check1.first)
                return check1

            val check2 = checkConstructors(c1.declaredConstructors, c2.declaredConstructors)
            if (!check2.first)
                return check2

            val check3 = checkConstructors(arrayOf(c1.enclosingConstructor), arrayOf(c2.enclosingConstructor))
            if (!check3.first)
                return check3


            val checkMethods: (Array<Method>, Array<Method>) -> Pair<Boolean, Error?> = { methods1, methods2->
                for (m1 in methods1)
                    for (m2 in methods2) {
                        try {
                            if (!m1.isAccessible || !m2.isAccessible) break

                            if (m1.defaultValue != m2.defaultValue)
                                Pair(false, Error("Some problems with method defaultValue"))

                            if (m1.name != m2.name)
                                Pair(false, Error("Some problems with method name"))

                            if (m1.returnType != m2.returnType)
                                Pair(false, Error("Some problems with method returnType"))

                            if (m1.genericReturnType != m2.genericReturnType)
                                Pair(false, Error("Some problems with method genericReturnType"))

                            val method1Parameters = m1.parameters.map { it.type.newInstance() }
                            val method2Parameters = m2.parameters.map { it.type.newInstance() }

                            val some1 = m1.invoke(c1, method1Parameters)
                            val some2 = m2.invoke(c2, method2Parameters)

                            if (some1 != some2)
                                Pair(false, Error("Some problems with componentType"))

                            if (some1.equals(some2) != some2.equals(some1))
                                Pair(false, Error("Some problems with equals methods"))

                            if (some1.hashCode() != some2.hashCode())
                                Pair(false, Error("Some problems with hashCode methods"))

                            if (some1.toString() != some2.toString())
                                Pair(false, Error("Some problems with toString methods"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                Pair(true, null)
            }

            val pair1 = checkMethods(c1.methods, c2.methods)
            if (!pair1.first)
                return pair1

            val pair2 = checkMethods(c1.declaredMethods, c2.declaredMethods)
            if (!pair2.first)
                return pair2

            val checkFields: (Array<out Field>, Array<out Field>) -> Pair<Boolean, Error?> = { fields1, fields2->
                for (f1 in fields1)
                    for (f2 in fields2) {
                        try {
                            if (!f1.isAccessible || !f2.isAccessible) break

                            if (f1.type != f2.type)
                                Pair(false, Error("Some problems with field type"))

                            if (f1.name != f2.name)
                                Pair(false, Error("Some problems with field name"))

                            if (f1.get(c1) != f2.get(c2))
                                Pair(false, Error("Some problems with field value"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                Pair(true, null)
            }

            val f1 = checkFields(c1.fields, c2.fields)
            if (!f1.first)
                return f1

            val f2 = checkFields(c1.declaredFields, c2.declaredFields)
            if (!f2.first)
                return f2

            return Pair(true, null)
        }
    }
}