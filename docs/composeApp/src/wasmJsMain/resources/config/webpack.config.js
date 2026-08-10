const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    mode: "development",
    entry: {
    	// CSS
    	iconCss: "src/jsMain/resources/style/icon.css",
    	owleCss: "src/jsMain/resources/style/owle.css",
    	// ICONS: ELEMENTS
    	addItem: "src/jsMain/resources/icons/element/add-item.svg",
    	arrowLeft: "src/jsMain/resources/icons/element/arrow-left.svg",
    	check: "src/jsMain/resources/icons/element/check.svg",
    	clone: "src/jsMain/resources/icons/element/clone.svg",
    	collapseFullscreen: "src/jsMain/resources/icons/element/collapse-fullscreen.svg",
    	collapseSection: "src/jsMain/resources/icons/element/collapse-section.svg",
    	collapseAll: "src/jsMain/resources/icons/element/collapse_all.svg",
    	data: "src/jsMain/resources/icons/element/data.svg",
    	dataItem: "src/jsMain/resources/icons/element/data-item.svg",
    	dataItemAdd: "src/jsMain/resources/icons/element/data-item-add.svg",
    	dataItemEdit: "src/jsMain/resources/icons/element/data-item-edit.svg",
    	dataItemReadonly: "src/jsMain/resources/icons/element/data-item-readonly.svg",
    	dataItemSearch: "src/jsMain/resources/icons/element/data-item-search.svg",
    	dataSearch: "src/jsMain/resources/icons/element/data-search.svg",
    	dataType: "src/jsMain/resources/icons/element/data-type.svg",
    	edit: "src/jsMain/resources/icons/element/edit.svg",
    	errorWatch: "src/jsMain/resources/icons/element/error-watch.svg",
    	expandAll: "src/jsMain/resources/icons/element/expand-all.svg",
    	expandFullscreen: "src/jsMain/resources/icons/element/expand-fullscreen.svg",
    	expandItem: "src/jsMain/resources/icons/element/expand-item.svg",
    	expandSection: "src/jsMain/resources/icons/element/expand-section.svg",
    	filter: "src/jsMain/resources/icons/element/filter.svg",
    	filterOff: "src/jsMain/resources/icons/element/filter-off.svg",
    	folder: "src/jsMain/resources/icons/element/folder.svg",
    	folderRefresh: "src/jsMain/resources/icons/element/folder-refresh.svg",
    	fn: "src/jsMain/resources/icons/element/function.svg",
    	fns: "src/jsMain/resources/icons/element/functions.svg",
    	inspectLater: "src/jsMain/resources/icons/element/inspect-later.svg",
    	inspector: "src/jsMain/resources/icons/element/inspector.svg",
    	inspectorBad: "src/jsMain/resources/icons/element/inspector-bad.svg",
    	inspectorClass: "src/jsMain/resources/icons/element/inspector-class.svg",
    	inspectors: "src/jsMain/resources/icons/element/inspectors.svg",
    	junction: "src/jsMain/resources/icons/element/junction.svg",
    	libraries: "src/jsMain/resources/icons/element/libraries.svg",
    	param: "src/jsMain/resources/icons/element/param.svg",
    	paste: "src/jsMain/resources/icons/element/paste.svg",
    	pencil: "src/jsMain/resources/icons/element/pencil.svg",
    	phase: "src/jsMain/resources/icons/element/phase.svg",
    	phaseAdd: "src/jsMain/resources/icons/element/phase-add.svg",
    	phases: "src/jsMain/resources/icons/element/phases.svg",
    	provision: "src/jsMain/resources/icons/element/provision.svg",
    	provisions: "src/jsMain/resources/icons/element/provisions.svg",
    	removeItem: "src/jsMain/resources/icons/element/remove-item.svg",
    	revision: "src/jsMain/resources/icons/element/revision.svg",
    	revisions: "src/jsMain/resources/icons/element/revisions.svg",
    	settings: "src/jsMain/resources/icons/element/settings.svg",
    	step: "src/jsMain/resources/icons/element/step.svg",
    	stepAdd: "src/jsMain/resources/icons/element/step-add.svg",
    	stepNext: "src/jsMain/resources/icons/element/step-next.svg",
    	stepRemove: "src/jsMain/resources/icons/element/step-remove.svg",
    	stepSettings: "src/jsMain/resources/icons/element/step-settings.svg",
    	steps: "src/jsMain/resources/icons/element/steps.svg",
    	trash: "src/jsMain/resources/icons/element/trash.svg",
    	workflow: "src/jsMain/resources/icons/element/workflow.svg",
    	workspace: "src/jsMain/resources/icons/element/workspace.svg",
    	// ELEMENTS: PAYMENT
    	ach: "src/jsMain/resources/icons/payment/ach.svg",
    	alipay: "src/jsMain/resources/icons/payment/alipay.svg",
    	amex: "src/jsMain/resources/icons/payment/amex.svg",
    	applePay: "src/jsMain/resources/icons/payment/apple_pay.svg",
    	bacsDd: "src/jsMain/resources/icons/payment/bacs_dd.svg",
    	bancontact: "src/jsMain/resources/icons/payment/bancontact.svg",
    	becsDd: "src/jsMain/resources/icons/payment/becs_dd.svg",
    	blik: "src/jsMain/resources/icons/payment/blik.svg",
    	caPad: "src/jsMain/resources/icons/payment/ca_pad.svg",
    	cartesBancaires: "src/jsMain/resources/icons/payment/cartes_bancaires.svg",
    	cashApp: "src/jsMain/resources/icons/payment/cash_app.svg",
    	chUnionPay: "src/jsMain/resources/icons/payment/ch_union_pay.svg",
    	cqRemCom: "src/jsMain/resources/icons/payment/cq_rem_com.svg",
    	diners: "src/jsMain/resources/icons/payment/diners.svg",
    	discover: "src/jsMain/resources/icons/payment/discover.svg",
    	eps: "src/jsMain/resources/icons/payment/eps.svg",
    	fpx: "src/jsMain/resources/icons/payment/fpx.svg",
    	googlePay: "src/jsMain/resources/icons/payment/google_pay.svg",
    	grabpay: "src/jsMain/resources/icons/payment/grabpay.svg",
    	ideal: "src/jsMain/resources/icons/payment/ideal.svg",
    	jcb: "src/jsMain/resources/icons/payment/jcb.svg",
    	link: "src/jsMain/resources/icons/payment/link.svg",
    	mastercard: "src/jsMain/resources/icons/payment/mastercard.svg",
    	mobilepay: "src/jsMain/resources/icons/payment/mobilepay.svg",
    	p24: "src/jsMain/resources/icons/payment/p24.svg",
    	paynow: "src/jsMain/resources/icons/payment/paynow.svg",
    	paypal: "src/jsMain/resources/icons/payment/paypal.svg",
    	promptpay: "src/jsMain/resources/icons/payment/promptpay.svg",
    	revolutPay: "src/jsMain/resources/icons/payment/revolut_pay.svg",
    	sepaDd: "src/jsMain/resources/icons/payment/sepa_dd.svg",
    	twint: "src/jsMain/resources/icons/payment/twint.svg",
    	visa: "src/jsMain/resources/icons/payment/visa.svg",
    	wechatPay: "src/jsMain/resources/icons/payment/wechat_pay.svg",
    	// ICONS : OWL-E PARTS
    	owleBeak: "src/jsMain/resources/icons/owle/owle-beak.svg",
    	owleEyeInnerLeft: "src/jsMain/resources/icons/owle/owle-eye-inner-left.svg",
    	owleEyeInnerRight: "src/jsMain/resources/icons/owle/owle-eye-inner-right.svg",
    	owleEyeOuterLeft: "src/jsMain/resources/icons/owle/owle-eye-outer-left.svg",
    	owleEyeOuterRight: "src/jsMain/resources/icons/owle/owle-eye-outer-right.svg",
    	owleEyePupilLeft: "src/jsMain/resources/icons/owle/owle-eye-pupil-left.svg",
    	owleEyePupilRight: "src/jsMain/resources/icons/owle/owle-eye-pupil-right.svg",
    	// ICONS : ROOT
    	favicon: "src/jsMain/resources/icons/favicon.ico",
    	iconDefault: "src/jsMain/resources/icons/icon-btn-default.svg",
        iconHover: "src/jsMain/resources/icons/icon-btn-hover.svg",
        iconPress: "src/jsMain/resources/icons/icon-btn-press.svg",
        iconFocus: "src/jsMain/resources/icons/icon-btn-focus.svg",
    	// FONTS : JETBRAINS MONO
    	jetbrainsmonoBold: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_bold.ttf",
    	jetbrainsmonoBoldItalic: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_bold_italic.ttf",
    	jetbrainsmonoExtrabold: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_extrabold.ttf",
    	jetbrainsmonoExtraboldItalic: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_extrabold_italic.ttf",
    	jetbrainsmonoItalic: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_italic.ttf",
    	jetbrainsmonoMedium: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_medium.ttf",
    	jetbrainsmonoMediumItalic: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_medium_italic.ttf",
    	jetbrainsmonoRegular: "src/jsMain/resources/font/jetbrainsmono/jetbrainsmono_regular.ttf",
    	// FONTS : RUBIK
    	rubikBlack: "src/jsMain/resources/font/rubik/Rubik-Black.ttf",
    	rubikBlackItalic: "src/jsMain/resources/font/rubik/Rubik-BlackItalic.ttf",
    	rubikBold: "src/jsMain/resources/font/rubik/Rubik-Bold.ttf",
    	rubikBoldItalic: "src/jsMain/resources/font/rubik/Rubik-BoldItalic.ttf",
    	rubikExtraBold: "src/jsMain/resources/font/rubik/Rubik-ExtraBold.ttf",
    	rubikExtraBoldItalic: "src/jsMain/resources/font/rubik/Rubik-ExtraBoldItalic.ttf",
    	rubikItalic: "src/jsMain/resources/font/rubik/Rubik-Italic.ttf",
    	rubikLight: "src/jsMain/resources/font/rubik/Rubik-Light.ttf",
    	rubikLightItalic: "src/jsMain/resources/font/rubik/Rubik-LightItalic.ttf",
    	rubikMedium: "src/jsMain/resources/font/rubik/Rubik-Medium.ttf",
    	rubikMediumItalic: "src/jsMain/resources/font/rubik/Rubik-MediumItalic.ttf",
    	rubikRegular: "src/jsMain/resources/font/rubik/Rubik-Regular.ttf",
    	rubikSemiBold: "src/jsMain/resources/font/rubik/Rubik-SemiBold.ttf",
    	rubikSemiBoldItalic: "src/jsMain/resources/font/rubik/Rubik-SemiBoldItalic.ttf",
    	main: "src/jsMain/resources/index.html",
    	entryThing: "src/jsMain/js/EntryThing.tsx"
    },
    output: {
        filename: "[name].js",
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
//    resolveLoader: {
//        modules: [
//            path.resolve(__dirname, "build/js/packages/owle/node_modules"), // Add this directory
//            path.resolve(__dirname, "build/js/node_modules"), // Existing directory
//            "node_modules", // Default fallback
//        ],
//        alias: {
//            react: path.resolve(__dirname, "build/js/node_modules/react"), // Explicit alias for React
//        },
//    },
//    plugins: [
//        new HtmlWebpackPlugin({
//            template: "./composeApp/src/jsMain/resources/index.html", // Path to your HTML file
//            filename: "index.html", // Output HTML file
//        }),
//    ],
    module: {
        rules: [
            {
                test: /\.(ttf)$/,
                type: 'asset/resource',
                generator: {
                    filename: 'font/[name][ext]'
                }
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
                                    require("tailwindcss"), //path.resolve(__dirname, "build/js/node_modules/tailwindcss")),
                                    require("autoprefixer") //path.resolve(__dirname, "build/js/node_modules/autoprefixer")),
                                ],
                            },
                        },
                    },
                ],
            },
            {
                test: /\.svg$/,
                type: "asset",
                generator: {
                    filename: "icons/[name][ext]", // Maintain directory structure in output
                },
            },
            {
                test: /\.(ico|png|jpe?g|gif)$/,
                type: "asset",
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
