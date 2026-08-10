/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "./site/src/jsMain/kotlin/ui/**/*.kt",
     "./build/js/packages/owle-site/**/*.html",    // If HTML files are generated during the build
     "./build/js/packages/owle-site/**/*.js",      // If Tailwind classes are dynamically included in JS output
   ],
   safelist: [
    "bg-center"
    "bg-contain",
    "bg-gray-900",
    "bg-no-repeat",
    "duration-300",
    "flex",
    "flex-col",
    "flex-none",
    "flex-none",
    "flex-row",
    "font-medium",
    "h-12",
    "h-screen",
    "items-center",
    "overflow-y-auto",
    "px-4",
    "py-2",
    "space-x-2",
    "space-y-2",
    "space-y-24",
    "space-y-3"
    "space-y-8",
    "text-center",
    "text-gray-700",
    "text-sm",
    "text-white",
    "transition-transform",
    "w-12",
    "w-24",
    "w-full",
   ],
   theme: {
       extend: {
//         backgroundImage: {
//           'iconButtonDefault': "url('../icons/icon-btn-default.svg')",
//           'iconButtonHovered': "url('../icons/icon-btn-hover.svg')",
//           'iconButtonPressed': "url('../icons/icon-btn-press.svg')",
//           'iconButtonFocused': "url('../icons/icon-btn-focus.svg')",
//         },
       },
   },
   plugins: [],
  // ...
}