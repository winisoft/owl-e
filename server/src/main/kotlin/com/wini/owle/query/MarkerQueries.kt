package com.wini.owle.query

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.entity.environs.EnvMarkerComparisons
import com.wini.owle.entity.environs.EnvMarkerExpressions
import com.wini.owle.entity.environs.EnvMarkerLogicals
import com.wini.owle.environs.MarkerEnvironment
import com.wini.owle.environs.MarkerNode
import com.wini.owle.repo.ExposedTransactionExecutor
import com.wini.owle.repo.TransactionExecutor
import org.jetbrains.exposed.v1.jdbc.selectAll


/**
 * Reading a dependency's environment marker back out of the rows it was shredded into.
 *
 * A marker such as `python_version >= "3.8" and (sys_platform == "win32" or extra == "dev")` is
 * stored as five `env_marker_expressions` rows plus their bodies — good for querying ("which
 * requirements are conditional on Windows?" is an indexable predicate rather than a `LIKE` over
 * text) and useless until something reassembles it.
 *
 * That reassembly happens here, once, in a single pass over the requirement's rows rather than a
 * query per node. Evaluation is in [com.wini.owle.environs.MarkerNode], which has no database
 * dependency and can be tested without one.
 */
class MarkerQueries(
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
) {

    /**
     * The marker attached to [requirementId], or null when the dependency is unconditional.
     *
     * Most dependencies have no marker at all, and a null result means exactly that: the
     * requirement always applies.
     */
    fun markerFor(requirementId: ULong): MarkerNode? = transactions.run {
        val nodes = EnvMarkerExpressions
            .selectAll()
            .where { EnvMarkerExpressions.requirement eq requirementId }
            .map { row ->
                NodeRow(
                    id = row[EnvMarkerExpressions.id].value,
                    parentId = row[EnvMarkerExpressions.parent]?.value,
                    mode = row[EnvMarkerExpressions.mode]
                )
            }
        if (nodes.isEmpty()) return@run null

        val ids = nodes.map { it.id }.toSet()

        val logicals = EnvMarkerLogicals
            .selectAll()
            .where { EnvMarkerLogicals.expression inList ids }
            .associate { row ->
                row[EnvMarkerLogicals.expression].value to Triple(
                    row[EnvMarkerLogicals.operator],
                    row[EnvMarkerLogicals.leftChild].value,
                    row[EnvMarkerLogicals.rightChild].value
                )
            }

        val comparisons = EnvMarkerComparisons
            .selectAll()
            .where { EnvMarkerComparisons.expression inList ids }
            .associate { row ->
                row[EnvMarkerComparisons.expression].value to Triple(
                    row[EnvMarkerComparisons.variable],
                    row[EnvMarkerComparisons.operator],
                    row[EnvMarkerComparisons.value]
                )
            }

        val root = nodes.firstOrNull { it.parentId == null } ?: return@run null
        build(root.id, nodes.associateBy { it.id }, logicals, comparisons, mutableSetOf())
    }

    private data class NodeRow(
        val id: ULong,
        val parentId: ULong?,
        val mode: EnvMarkerExpressionMode
    )

    /**
     * Assembles one node and its children.
     *
     * [seen] guards against a cycle. `parent_id` is a plain self-reference with nothing preventing
     * one, and a marker that referred to itself would otherwise recurse until the stack gave out —
     * a malformed row should produce a missing branch, not a crash.
     */
    private fun build(
        id: ULong,
        nodes: Map<ULong, NodeRow>,
        logicals: Map<ULong, Triple<EnvMarkerLogicOp, ULong, ULong>>,
        comparisons: Map<ULong, Triple<EnvMarkerVariable?, EnvMarkerOperator, String>>,
        seen: MutableSet<ULong>
    ): MarkerNode? {
        if (!seen.add(id)) return null
        val node = nodes[id] ?: return null

        return when (node.mode) {
            EnvMarkerExpressionMode.LOGICAL -> {
                val (op, leftId, rightId) = logicals[id] ?: return null
                val left = build(leftId, nodes, logicals, comparisons, seen)
                val right = build(rightId, nodes, logicals, comparisons, seen)
                when {
                    left != null && right != null -> MarkerNode.Logical(op, left, right)
                    // a half-built branch is not an evaluable marker; the surviving side is
                    // returned so a partial tree still says something rather than nothing
                    else -> left ?: right
                }
            }

            EnvMarkerExpressionMode.COMPARATIVE -> {
                val (variable, op, value) = comparisons[id] ?: return null
                MarkerNode.Comparison(variable, op, value)
            }
        }
    }

    /**
     * Every requirement of a package, paired with its marker.
     *
     * The shape the environment view needs: a dependency list where each entry already knows
     * whether it applies here, rather than one query per requirement to find out.
     */
    fun markersForRequirements(requirementIds: List<ULong>): Map<ULong, MarkerNode?> =
        requirementIds.associateWith { markerFor(it) }

    /** Whether a requirement applies in [environment]. An absent marker always applies. */
    fun applies(requirementId: ULong, environment: MarkerEnvironment): Boolean =
        markerFor(requirementId)?.evaluate(environment) ?: true
}
