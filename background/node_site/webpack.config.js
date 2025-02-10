const path = require("path");

module.exports = {
    mode: "development",
    entry: {
        iconStylesIcon: "./src/jsMain/resources/public/styles/icon.css",
        mainStylesIcon: "./src/jsMain/resources/public/styles/index.css",
        tailwindStylesIcon: "./src/jsMain/resources/public/styles/tailwind.css",
        addItemIcon: "./src/jsMain/resources/public/icons/element/add-item.svg",
        arrowLeftIcon: "./src/jsMain/resources/public/icons/element/arrow-left.svg",
        checkIcon: "./src/jsMain/resources/public/icons/element/check.svg",
        cloneIcon: "./src/jsMain/resources/public/icons/element/clone.svg",
        collapseFullscreenIcon: "./src/jsMain/resources/public/icons/element/collapse-fullscreen.svg",
        collapseSectionIcon: "./src/jsMain/resources/public/icons/element/collapse-section.svg",
        collapseAllIcon: "./src/jsMain/resources/public/icons/element/collapse_all.svg",
        dataIcon: "./src/jsMain/resources/public/icons/element/data.svg",
        dataItemIcon: "./src/jsMain/resources/public/icons/element/data-item.svg",
        dataItemAddIcon: "./src/jsMain/resources/public/icons/element/data-item-add.svg",
        dataItemEditIcon: "./src/jsMain/resources/public/icons/element/data-item-edit.svg",
        dataItemReadonlyIcon: "./src/jsMain/resources/public/icons/element/data-item-readonly.svg",
        dataItemSearchIcon: "./src/jsMain/resources/public/icons/element/data-item-search.svg",
        dataSearchIcon: "./src/jsMain/resources/public/icons/element/data-search.svg",
        dataTypeIcon: "./src/jsMain/resources/public/icons/element/data-type.svg",
        editIcon: "./src/jsMain/resources/public/icons/element/edit.svg",
        errorWatchIcon: "./src/jsMain/resources/public/icons/element/error-watch.svg",
        expandAllIcon: "./src/jsMain/resources/public/icons/element/expand-all.svg",
        expandFullscreenIcon: "./src/jsMain/resources/public/icons/element/expand-fullscreen.svg",
        expandItemIcon: "./src/jsMain/resources/public/icons/element/expand-item.svg",
        expandSectionIcon: "./src/jsMain/resources/public/icons/element/expand-section.svg",
        filterIcon: "./src/jsMain/resources/public/icons/element/filter.svg",
        filterOffIcon: "./src/jsMain/resources/public/icons/element/filter-off.svg",
        folderIcon: "./src/jsMain/resources/public/icons/element/folder.svg",
        folderRefreshIcon: "./src/jsMain/resources/public/icons/element/folder-refresh.svg",
        functionIcon: "./src/jsMain/resources/public/icons/element/function.svg",
        functionsIcon: "./src/jsMain/resources/public/icons/element/functions.svg",
        inspectLaterIcon: "./src/jsMain/resources/public/icons/element/inspect-later.svg",
        inspectorIcon: "./src/jsMain/resources/public/icons/element/inspector.svg",
        inspectorBadIcon: "./src/jsMain/resources/public/icons/element/inspector-bad.svg",
        inspectorClassIcon: "./src/jsMain/resources/public/icons/element/inspector-class.svg",
        inspectorsIcon: "./src/jsMain/resources/public/icons/element/inspectors.svg",
        junctionIcon: "./src/jsMain/resources/public/icons/element/junction.svg",
        librariesIcon: "./src/jsMain/resources/public/icons/element/libraries.svg",
        paramIcon: "./src/jsMain/resources/public/icons/element/param.svg",
        pasteIcon: "./src/jsMain/resources/public/icons/element/paste.svg",
        pencilIcon: "./src/jsMain/resources/public/icons/element/pencil.svg",
        phaseIcon: "./src/jsMain/resources/public/icons/element/phase.svg",
        phaseAddIcon: "./src/jsMain/resources/public/icons/element/phase-add.svg",
        phasesIcon: "./src/jsMain/resources/public/icons/element/phases.svg",
        provisionIcon: "./src/jsMain/resources/public/icons/element/provision.svg",
        provisionsIcon: "./src/jsMain/resources/public/icons/element/provisions.svg",
        removeItemIcon: "./src/jsMain/resources/public/icons/element/remove-item.svg",
        revisionIcon: "./src/jsMain/resources/public/icons/element/revision.svg",
        revisionsIcon: "./src/jsMain/resources/public/icons/element/revisions.svg",
        settingsIcon: "./src/jsMain/resources/public/icons/element/settings.svg",
        stepIcon: "./src/jsMain/resources/public/icons/element/step.svg",
        stepAddIcon: "./src/jsMain/resources/public/icons/element/step-add.svg",
        stepNextIcon: "./src/jsMain/resources/public/icons/element/step-next.svg",
        stepRemoveIcon: "./src/jsMain/resources/public/icons/element/step-remove.svg",
        stepSettingsIcon: "./src/jsMain/resources/public/icons/element/step-settings.svg",
        stepsIcon: "./src/jsMain/resources/public/icons/element/steps.svg",
        trashIcon: "./src/jsMain/resources/public/icons/element/trash.svg",
        workflowIcon: "./src/jsMain/resources/public/icons/element/workflow.svg",
        workspaceIcon: "./src/jsMain/resources/public/icons/element/workspace.svg",
    },
    output: {
        filename: "[name].js",
        path: path.resolve(__dirname, "build/js/bundledComponents"),
        library: {
            type: "umd",
        },
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"], // Resolve these file extensions
        modules: [
            path.resolve(__dirname, "build/js/packages/owle/node_modules"), // Add this directory
            path.resolve(__dirname, "build/js/node_modules"), // Existing directory
            "node_modules", // Default fallback
        ],
        alias: {
            react: path.resolve(__dirname, "build/js/node_modules/react"), // Explicit alias for React
        },
    },
    resolveLoader: {
        modules: [
            path.resolve(__dirname, "build/js/packages/owle/node_modules"), // Add this directory
            path.resolve(__dirname, "build/js/node_modules"), // Existing directory
            "node_modules", // Default fallback
        ],
        alias: {
            react: path.resolve(__dirname, "build/js/node_modules/react"), // Explicit alias for React
        },
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx|ts)$/,
                use: "ts-loader",
                exclude: /node_modules/,
            },
            {
                test: /\.tsx?$/, // Match .ts and .tsx files
                exclude: /node_modules/,
                use: {
                    loader: "ts-loader", // Use ts-loader for TypeScript
                    options: {
                        transpileOnly: true, // Skip type-checking for faster builds
                    },
                },
            },
            {
                test: /\.css$/,
                use: [
                    "style-loader",
                    "css-loader",
                    {
                        loader: "postcss-loader",
                        options: {
                            postcssOptions: {
                                plugins: [
                                    require(path.resolve(__dirname, "build/js/node_modules/tailwindcss")),
                                    require(path.resolve(__dirname, "build/js/node_modules/autoprefixer")),
                                ],
                            },
                        },
                    },
                ],
            },
            // Loader for SVGs and other assets
            {
                test: /\.svg$/,
                type: "asset", // Use Webpack's asset modules for handling SVGs
                generator: {
                    filename: "icons/[name][ext]", // Maintain directory structure in output
                },
            },
            {
                test: /\.(ico|png|jpe?g|gif)$/,
                type: "asset/resource", // Handle other image types as resources
                generator: {
                    filename: "icons/[name][ext]",
                },
            },
            {
                test: /\.html$/,
                use: [
                    {
                        loader: "html-loader",
                    },
                ],
            },
        ],
    },
    externals: {
        react: "React", // Treat React as an external dependency
        "react-dom": "ReactDOM", // Treat ReactDOM as an external dependency
    },
};
