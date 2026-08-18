package com.wini.owle

import com.wini.owle.TestHelper
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.PythonVersions
import com.wini.owle.entity.environs.Versions
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.steps.DataPointWrites
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeGenericParams
import com.wini.owle.entity.typing.TypeInstantiationArgs
import com.wini.owle.entity.typing.TypeInstantiations
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.WorkflowRequirements
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.junit.jupiter.api.Tag


@Tag("integration")
abstract class CompleteTransformsExampleIntegration {

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin {
            modules(allModules)
            allowOverride(false)
        }

        TestHelper.connectToTestDb()

        TestHelper.resetAll()

        constructExampleData()
    }

    fun constructExampleData() {
        transaction {
            VersionEntity.new(1UL) {
                epoch = 0UL
                major = 1UL
                minor = 0UL
                micro = 0UL
                phase = PrereleasePhase.FINAL
                phaseNumber = null
                postNumber = null
                devNumber = null
                local = null
                hasOverflow = false
            }
            TransformEntity.new(1UL) {
                name = "Take Unique Values"
                description = "The distinct unique values from this collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">students</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''Ada''</span>, <span style=\"color:#6A8759\">''Ben''</span>, <span style=\"color:#6A8759\">''Ada''</span>, <span style=\"color:#6A8759\">''Clara''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">set</span>(<span style=\"color:#A9B7C6\">students</span>)<span style=\"color:#A5C25C\">{</span><span style=\"color:#6A8759\">''Ada''</span>, <span style=\"color:#6A8759\">''Ben''</span>, <span style=\"color:#6A8759\">''Clara''</span><span style=\"color:#A5C25C\">}</span></pre>"
            }

            TransformEntity.new(2UL) {
                name = "Flattened"
                description = "Transforms this collection of items into a single collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">items</span> = <span style=\"color:#CC7832\">[[</span><span style=\"color:#6897BB\">1</span>, <span style=\"color:#6897BB\">2</span><span style=\"color:#CC7832\">], [</span><span style=\"color:#6897BB\">3</span>, <span style=\"color:#6897BB\">4</span><span style=\"color:#CC7832\">], [</span><span style=\"color:#6897BB\">5</span><span style=\"color:#CC7832\">]]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#CC7832\">[</span><span style=\"color:#A9B7C6\">item</span> <span style=\"color:#CC7832\">for</span> <span style=\"color:#A9B7C6\">group</span> <span style=\"color:#CC7832\">in</span> <span style=\"color:#A9B7C6\">items</span> <span style=\"color:#CC7832\">for</span> <span style=\"color:#A9B7C6\">item</span> <span style=\"color:#CC7832\">in</span> <span style=\"color:#A9B7C6\">group</span><span style=\"color:#CC7832\">]</span><span style=\"color:#A5C25C\">[</span><span style=\"color:#6897BB\">1</span>, <span style=\"color:#6897BB\">2</span>, <span style=\"color:#6897BB\">3</span>, <span style=\"color:#6897BB\">4</span>, <span style=\"color:#6897BB\">5</span><span style=\"color:#A5C25C\">]</span></pre>"
            }

            TransformEntity.new(3UL) {
                name = "Reverse Order"
                description = "The items in this collection, sorted into the reserve order"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">payments</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">100</span>, <span style=\"color:#6897BB\">200</span>, <span style=\"color:#6897BB\">150</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">list</span>(<span style=\"color:#A9B7C6\">reversed</span>(<span style=\"color:#A9B7C6\">payments</span>))<span style=\"color:#A5C25C\">[</span><span style=\"color:#6897BB\">150</span>, <span style=\"color:#6897BB\">200</span>, <span style=\"color:#6897BB\">100</span><span style=\"color:#A5C25C\">]</span></pre>"
            }

            TransformEntity.new(4UL) {
                name = "Enumerated"
                description =
                    "The items of this collection transformed into a tuple pair of values, an index representing its position and the original element"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">vehicles</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''bike''</span>, <span style=\"color:#6A8759\">''car''</span>, <span style=\"color:#6A8759\">''bus''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">list</span>(<span style=\"color:#A9B7C6\">enumerate</span>(<span style=\"color:#A9B7C6\">vehicles</span>))<span style=\"color:#A5C25C\">[(</span><span style=\"color:#6897BB\">0</span>, <span style=\"color:#6A8759\">''bike''</span><span style=\"color:#A5C25C\">), (</span><span style=\"color:#6897BB\">1</span>, <span style=\"color:#6A8759\">''car''</span><span style=\"color:#A5C25C\">), (</span><span style=\"color:#6897BB\">2</span>, <span style=\"color:#6A8759\">''bus''</span><span style=\"color:#A5C25C\">)]</span></pre>"
            }

            TransformEntity.new(5UL) {
                name = "Smallest Value"
                description = "The smallest value among the elements in this collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">temperatures</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">72</span>, <span style=\"color:#6897BB\">68</span>, <span style=\"color:#6897BB\">75</span>, <span style=\"color:#6897BB\">70</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">min</span>(<span style=\"color:#A9B7C6\">temperatures</span>)<span style=\"color:#6897BB\">68</span></pre>"
            }

            TransformEntity.new(6UL) {
                name = "Biggest Value"
                description = "The largest value among the elements in this collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">scores</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">88</span>, <span style=\"color:#6897BB\">95</span>, <span style=\"color:#6897BB\">70</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">max</span>(<span style=\"color:#A9B7C6\">scores</span>)<span style=\"color:#6897BB\">95</span></pre>"
            }

            TransformEntity.new(7UL) {
                name = "Count of Elements"
                description = "A count of the number of items present in this collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">videos</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''intro.mp4''</span>, <span style=\"color:#6A8759\">''lesson1.mp4''</span>, <span style=\"color:#6A8759\">''lesson2.mp4''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">len</span>(<span style=\"color:#A9B7C6\">videos</span>)<span style=\"color:#6897BB\">3</span></pre>"
            }

            TransformEntity.new(8UL) {
                name = "Sum of Elements"
                description = "The sum of the elements in this collection"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">amounts</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">19.99</span>, <span style=\"color:#6897BB\">5.00</span>, <span style=\"color:#6897BB\">3.50</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">sum</span>(<span style=\"color:#A9B7C6\">amounts</span>)<span style=\"color:#6897BB\">28.49</span></pre>"
            }

            TransformEntity.new(9UL) {
                name = "Item at Index"
                description = "The item at a specified index position"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">cities</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''Paris''</span>, <span style=\"color:#6A8759\">''Tokyo''</span>, <span style=\"color:#6A8759\">''Cairo''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">cities</span>[<span style=\"color:#6897BB\">1</span>]<span style=\"color:#6A8759\">''Tokyo''</span></pre>"
            }

            TransformEntity.new(10UL) {
                name = "Item with Key (dict[str, Any])"
                description = "The value found under a key matching the provided value"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">profile</span> = <span style=\"color:#A5C25C\">{</span><span style=\"color:#6A8759\">''name''</span>: <span style=\"color:#6A8759\">''Elena''</span>, <span style=\"color:#6A8759\">''age''</span>: <span style=\"color:#6897BB\">31</span><span style=\"color:#A5C25C\">}</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">profile</span>[<span style=\"color:#6A8759\">''age''</span>]<span style=\"color:#6897BB\">31</span></pre>"
            }

            TransformEntity.new(11UL) {
                name = "Split"
                description =
                    "Divides this string into a list of values where the specified char is found in its content"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">sentence</span> = <span style=\"color:#6A8759\">''alpha, beta, gamma''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">sentence</span>.<span style=\"color:#A9B7C6\">split</span>(<span style=\"color:#6A8759\">'', ''</span>)<span style=\"color:#A5C25C\">[</span><span style=\"color:#6A8759\">''alpha''</span>, <span style=\"color:#6A8759\">''beta''</span>, <span style=\"color:#6A8759\">''gamma''</span><span style=\"color:#A5C25C\">]</span></pre>"
            }

            TransformEntity.new(12UL) {
                name = "Join"
                description = "Combines this list of values into a string with a specified delimiter between them"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">tags</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''html''</span>, <span style=\"color:#6A8759\">''css''</span>, <span style=\"color:#6A8759\">''js''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#6A8759\">'', ''</span>.<span style=\"color:#A9B7C6\">join</span>(<span style=\"color:#A9B7C6\">tags</span>)<span style=\"color:#6A8759\">''html, css, js''</span></pre>"
            }

            TransformEntity.new(13UL) {
                name = "Items with Keys"
                description = "A list of values found under keys matching the provided set of values"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">row</span> = <span style=\"color:#A5C25C\">{</span><span style=\"color:#6A8759\">''id''</span>: <span style=\"color:#6897BB\">42</span>, <span style=\"color:#6A8759\">''status''</span>: <span style=\"color:#6A8759\">''ok''</span>, <span style=\"color:#6A8759\">''score''</span>: <span style=\"color:#6897BB\">9.5</span><span style=\"color:#A5C25C\">}</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#CC7832\">[</span><span style=\"color:#A9B7C6\">row</span>[<span style=\"color:#A9B7C6\">k</span>] <span style=\"color:#CC7832\">for</span> <span style=\"color:#A9B7C6\">k</span> <span style=\"color:#CC7832\">in</span> <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''status''</span>, <span style=\"color:#6A8759\">''score''</span><span style=\"color:#CC7832\">]]</span><span style=\"color:#A5C25C\">[</span><span style=\"color:#6A8759\">''ok''</span>, <span style=\"color:#6897BB\">9.5</span><span style=\"color:#A5C25C\">]</span></pre>"
            }

            TransformEntity.new(14UL) {
                name = "Is Equal To (Numeric)"
                description = "Returns True if the numeric value is equal to the provided value, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">score</span> = <span style=\"color:#6897BB\">87</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">score</span> == <span style=\"color:#6897BB\">87</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(15UL) {
                name = "Is Less Than (Numeric)"
                description = "Returns True if the numeric value is less than the provided value, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">balance</span> = <span style=\"color:#6897BB\">42.50</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">balance</span> &lt; <span style=\"color:#6897BB\">100</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(16UL) {
                name = "Is Greater Than (Numeric)"
                description = "Returns True if the numeric value is greater than the provided value, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">speed</span> = <span style=\"color:#6897BB\">85</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">speed</span> &gt; <span style=\"color:#6897BB\">60</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(17UL) {
                name = "Is Less Than or Equal to (Numeric)"
                description =
                    "Returns True if the numeric value is less than or equal to the provided value, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">downloads</span> = <span style=\"color:#6897BB\">10</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">downloads</span> &lt;= <span style=\"color:#6897BB\">10</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(18UL) {
                name = "Is Greater Than or Equal to (Numeric)"
                description =
                    "Returns True if the numeric value is greater than or equal to the provided value, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">level</span> = <span style=\"color:#6897BB\">5</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">level</span> &gt;= <span style=\"color:#6897BB\">3</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(19UL) {
                name = "Is Equal To (String)"
                description =
                    "Returns True if the string value is exactly equal to the provided string, otherwise False"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">status</span> = <span style=\"color:#6A8759\">''complete''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">status</span> == <span style=\"color:#6A8759\">''complete''</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(20UL) {
                name = "Is Less Than (String)"
                description = "Returns True if the string value comes before the provided string in lexicographic order"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">a</span> = <span style=\"color:#6A8759\">''apple''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">a</span> &lt; <span style=\"color:#6A8759\">''banana''</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(21UL) {
                name = "Is Greater Than (String)"
                description = "Returns True if the string value comes after the provided string in lexicographic order"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">x</span> = <span style=\"color:#6A8759\">''zebra''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">x</span> &gt; <span style=\"color:#6A8759\">''yak''</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(22UL) {
                name = "Is Less Than or Equal to (String)"
                description =
                    "Returns True if the string value comes before or is equal to the provided string in lexicographic order"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">user_role</span> = <span style=\"color:#6A8759\">''admin''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">user_role</span> &lt;= <span style=\"color:#6A8759\">''editor''</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(23UL) {
                name = "Is Greater Than or Equal to (String)"
                description =
                    "Returns True if the string value comes after or is equal to the provided string in lexicographic order"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">label</span> = <span style=\"color:#6A8759\">''high''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">label</span> &gt;= <span style=\"color:#6A8759\">''high''</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(24UL) {
                name = "Cast to int"
                description =
                    "Attempts to convert the value to an integer, returning None if conversion is not possible"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">value</span> = <span style=\"color:#6A8759\">''42''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">int</span>(<span style=\"color:#A9B7C6\">value</span>)<span style=\"color:#6897BB\">42</span></pre>"
            }

            TransformEntity.new(25UL) {
                name = "Cast to bool"
                description = "Converts the value to a boolean according to standard Python truthiness rules"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">flag</span> = <span style=\"color:#6A8759\">''yes''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">bool</span>(<span style=\"color:#A9B7C6\">flag</span>)<span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(26UL) {
                name = "Cast to float"
                description =
                    "Attempts to convert the value to a floating-point number, returning None if conversion fails"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">size</span> = <span style=\"color:#6A8759\">''3.14''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">float</span>(<span style=\"color:#A9B7C6\">size</span>)<span style=\"color:#6897BB\">3.14</span></pre>"
            }

            TransformEntity.new(27UL) {
                name = "To String"
                description = "Converts the value into its string representation using Python’s str()"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">hour</span> = <span style=\"color:#6897BB\">9</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">str</span>(<span style=\"color:#A9B7C6\">hour</span>)<span style=\"color:#6A8759\">''9''</span></pre>"
            }

            TransformEntity.new(28UL) {
                name = "Object Identity (Python "
                description = "Returns True if two values refer to the same object in memory (Python identity)"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">flag</span> = <span style=\"color:#CC7832\">None</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">flag</span> is <span style=\"color:#CC7832\">None</span><span style=\"color:#CC7832\">True</span></pre>"
            }

            TransformEntity.new(29UL) {
                name = "Object Identity Negation (Python "
                description = "s not"
                examples =
                    "Returns True if two values do not refer to the same object in memory (Python identity negation)','<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">flag</span> = <span style=\"color:#CC7832\">None</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">flag</span> is not <span style=\"color:#CC7832\">None</span><span style=\"color:#CC7832\">False</span></pre>"
            }

            TransformEntity.new(30UL) {
                name = "Mean (Average)"
                description = "The average of all numeric values in this collection; returns None for empty collections"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">grades</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">80</span>, <span style=\"color:#6897BB\">85</span>, <span style=\"color:#6897BB\">90</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">sum</span>(<span style=\"color:#A9B7C6\">grades</span>) / <span style=\"color:#A9B7C6\">len</span>(<span style=\"color:#A9B7C6\">grades</span>)<span style=\"color:#6897BB\">85.0</span></pre>"
            }

            TransformEntity.new(31UL) {
                name = "Median Value"
                description =
                    "The median value from a collection of numeric or sortable elements; returns None for empty collections"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#CC7832\">import</span> <span style=\"color:#A9B7C6\">statistics</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">votes</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6897BB\">3</span>, <span style=\"color:#6897BB\">5</span>, <span style=\"color:#6897BB\">2</span>, <span style=\"color:#6897BB\">8</span>, <span style=\"color:#6897BB\">4</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">statistics</span>.<span style=\"color:#A9B7C6\">median</span>(<span style=\"color:#A9B7C6\">votes</span>)<span style=\"color:#6897BB\">4</span></pre>"
            }

            TransformEntity.new(32UL) {
                name = "Mode (Most Frequent Item)"
                description =
                    "The most frequently occurring item in the collection; returns None if the collection is empty or there is no single most frequent item"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#CC7832\">import</span> <span style=\"color:#A9B7C6\">statistics</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">colors</span> = <span style=\"color:#CC7832\">[</span><span style=\"color:#6A8759\">''red''</span>, <span style=\"color:#6A8759\">''blue''</span>, <span style=\"color:#6A8759\">''red''</span>, <span style=\"color:#6A8759\">''green''</span><span style=\"color:#CC7832\">]</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">statistics</span>.<span style=\"color:#A9B7C6\">mode</span>(<span style=\"color:#A9B7C6\">colors</span>)<span style=\"color:#6A8759\">''red''</span></pre>"
            }

            TransformEntity.new(33UL) {
                name = "Substring Before First Occurence of"
                description =
                    "Returns the substring found before the first occurrence of the specified delimiter; if the delimiter is not found, returns the entire string"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">email</span> = <span style=\"color:#6A8759\">''juno.rae@example.org''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">email</span>.<span style=\"color:#A9B7C6\">split</span>(<span style=\"color:#6A8759\">''@''</span>)[<span style=\"color:#6897BB\">0</span>]<span style=\"color:#6A8759\">''juno.rae''</span></pre>"
            }

            TransformEntity.new(34UL) {
                name = "Substring After Last Occurrence of"
                description =
                    "Returns the substring found after the last occurrence of the specified delimiter; if the delimiter is not found, returns None"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">path</span> = <span style=\"color:#6A8759\">''/home/user/photos/image.jpg''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">path</span>.<span style=\"color:#A9B7C6\">rpartition</span>(<span style=\"color:#6A8759\">''/''</span>)[<span style=\"color:#6897BB\">2</span>]<span style=\"color:#6A8759\">''image.jpg''</span></pre>"
            }

            TransformEntity.new(35UL) {
                name = "Substring From Index"
                description =
                    "Returns a substring starting at the provided index; if the index is out of bounds, returns None instead of raising an error"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">query</span> = <span style=\"color:#6A8759\">''search=apple&sort=asc''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">query</span>[<span style=\"color:#6897BB\">7</span>:]<span style=\"color:#6A8759\">''apple&sort=asc''</span></pre>"
            }

            TransformEntity.new(36UL) {
                name = "Substring Between Indexes"
                description =
                    "Returns a substring between two provided indexes; if indexes are out of bounds or ordered incorrectly, returns None instead of throwing an error"
                examples =
                    "<pre style=\"background-color:#000000; color:#A9B7C6; font-family:Consolas, monospace\"><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">username</span> = <span style=\"color:#6A8759\">''Dr. Lucinda Featherstone, PhD''</span><span style=\"color:#BBB529\">&gt;&gt;&gt;</span> <span style=\"color:#A9B7C6\">username</span>[<span style=\"color:#6897BB\">4</span>:<span style=\"color:#6897BB\">11</span>]<span style=\"color:#6A8759\">''Lucinda''</span></pre>"
            }

            TypeNodeEntity.new(1UL) {
                namespace = "builtins"
                name = "str"
                alias = null
                isGeneric = false

            }

            TransformArgEntity.new(1UL) {
                name = ""
                description = ""
                transform = TransformEntity[36UL]
                typeNode = TypeNodeEntity[1UL]
            }
        }

    }

}