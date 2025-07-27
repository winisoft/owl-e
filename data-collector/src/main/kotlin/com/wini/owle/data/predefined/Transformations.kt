package com.wini.owle.data.predefined

import com.wini.owle.dto.transforms.Transform

object Transformations {

    val predefined: Set<Transform> = setOf(
        Transform(
            1, "Result Unique Values", "The tool's result, filtered to include only unique elements.",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #6a9955;\">list</span>(<span style=\"color: #dcdcaa;\">set</span>(<span style=\"color: #9cdcfe;\">result</span>))",
            null
        ),
        Transform(
            2,
            "Result Flattened",
            "The tool's result, with sub-lists combined to form a single list",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #9cdcfe;\">x</span> <span style=\"color: #c586c0;\">for</span> <span style=\"color: #9cdcfe;\">list</span> <span style=\"color: #c586c0;\">in</span> <span style=\"color: #9cdcfe;\">result</span> <span style=\"color: #c586c0;\">for</span> <span style=\"color: #9cdcfe;\">x</span> <span style=\"color: #c586c0;\">in</span> <span style=\"color: #9cdcfe;\">list</span>",
            null
        ),
        Transform(
            3,
            "Result Reversed",
            "The tool's result, reversed in order",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #9cdcfe;\">result</span>[<span style=\"color: #b5cea8;\">:</span><span style=\"color: #b5cea8;\">:-1</span>]",
            null
        ),
        Transform(
            4,
            "Result Enumerated",
            "The tool's result with an index added to each element",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #6a9955;\">list</span>(<span style=\"color: #dcdcaa;\">enumerate</span>(<span style=\"color: #9cdcfe;\">result</span>))",
            null
        ),
        Transform(
            5,
            "Result Min",
            "The smallest result among the elements in the tool's result",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #dcdcaa;\">min</span>(<span style=\"color: #9cdcfe;\">result</span>)",
            null
        ),
        Transform(
            6,
            "Result Max",
            "The largest result among the elements in the tool's result",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #dcdcaa;\">max</span>(<span style=\"color: #9cdcfe;\">result</span>)",
            null
        ),
        Transform(
            7,
            "Result Count",
            "The tool's result, counted by occurrences of each element",
            "<span style=\"color: white; font-weight: bold;\">ex:</span> <span style=\"color: #9cdcfe;\">collections</span>.<span style=\"color: #dcdcaa;\">Counter</span>(<span style=\"color: #9cdcfe;\">result</span>)",
            null
        ),
        /*
        (8, 'Result Sum', 'The sum of the elements in the tool\'s resulting value', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #dcdcaa;">sum</span>(<span style="color: #9cdcfe;">result</span>)', NULL),

         */
        Transform(
            8, "", "", "", null
        ),
        /*
        (9, 'Result Cast', 'The tool\'s result, cast into a different data type', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #dcdcaa;">int</span>(<span style="color: #9cdcfe;">result</span>)', NULL),
         */
        Transform(
            9, "", "", "", null
        ),
        /*
        (10, 'Result Exact', 'The tool\'s result, unmodified The tool\'s result, unmodified', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #9cdcfe;">result</span>', NULL),

         */
        Transform(
            10, "", "", "", null
        ),
        /*
        (11, 'Result Indexed', 'A specified index of the result', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #9cdcfe;">result</span>[<span style="color: #b5cea8;">0</span>]', NULL),

         */
        Transform(
            11, "", "", "", null
        ),
        /*
        (12, 'Result By Key', 'The result\'s value stored under the specified key', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #9cdcfe;">result</span>[<span style="color: #ce9178;">\'name\'</span>]', NULL),

         */
        Transform(
            12, "", "", "", null
        ),
        /*
        (13, 'Result Bool Negated', 'The tool\'s bool result negated', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #569cd6;">not</span> <span style="color: #9cdcfe;">result</span>', NULL),

         */
        Transform(
            13, "", "", "", null
        ),
        /*
        (14, 'Result Split', 'The tool\'s result, split into a list of elements delimited by some character', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #9cdcfe;">result</span>.<span style="color: #dcdcaa;">split</span>(<span style="color: #ce9178;">\'|\'</span>)', NULL),

         */
        Transform(
            14, "", "", "", null
        ),
        /*
        (15, 'Result Filtered', 'The tool\'s result, split into a list of elements delimited by some character', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #9cdcfe;">x</span> <span style="color: #c586c0;">for</span> <span style="color: #9cdcfe;">x</span> <span style="color: #c586c0;">in</span> <span style="color: #9cdcfe;">result</span> <span style="color: #c586c0;">if</span> <span style="color: #dcdcaa;">f</span>(<span style="color: #9cdcfe;">x</span>)', NULL),

         */
        Transform(
            15, "", "", "", null
        ),
        /*
        (16, 'Result Joined', 'The tool\'s result, formatted into a list separated by some character', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #ce9178;">\",\"</span>.<span style="color: #dcdcaa;">join</span>(<span style="color: #dcdcaa;">str</span>(<span style="color: #9cdcfe;">x</span>) <span style="color: #c586c0;">for</span> <span style="color: #9cdcfe;">x</span> <span style="color: #c586c0;">in</span> <span style="color: #9cdcfe;">result</span>)', NULL),

         */
        Transform(
            16, "", "", "", null
        ),
        /*
        (17, 'Result Formatted', 'The tool\'s result, provided to fulfill the format of a string', '<span style="color: white; font-weight: bold;">ex:</span> <span style="color: #ce9178;">f\"The result is {result}\"</span>', NULL);
         */
        Transform(
            17, "", "", "", null
        )
    )

}