// import React, { useState } from 'react';
// import { ArrowRight } from 'lucide-react';
// import { Step } from '../model/Step';

// interface StepProps {
//   step: Step;
// }

// const StepUI: React.FC<StepProps> = ({ step }) => {
//   const [isHovered, setIsHovered] = useState(false);

//   return (
//     <div 
//       className="bg-gray-600 p-2 rounded-lg mt-2 flex justify-between items-center"
//       onMouseEnter={() => setIsHovered(true)}
//       onMouseLeave={() => setIsHovered(false)}
//     >
//       <div className="flex items-center">
//         <ArrowRight className="mr-2 text-blue-300" size={16} />
//         <span className="text-white">{step.name}</span>
//       </div>
//       {isHovered && (
//         <div className="flex space-x-2">
//           <button className="text-blue-300 hover:text-blue-100">Edit</button>
//           <button className="text-blue-300 hover:text-blue-100">Delete</button>
//         </div>
//       )}
//     </div>
//   );
// };

// export default StepUI;