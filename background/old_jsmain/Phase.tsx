// import React, { useState } from 'react';
// import StepUI from './Step';
// import { Phase } from '../model/Phase';


// interface PhaseProps {
//   phase: Phase;
// }

// const PhaseUI: React.FC<PhaseProps> = ({ phase }) => {

//   const [isExpanded, setIsExpanded] = useState(false);

//   return (
//     <div className="bg-gray-700 p-3 rounded-lg mt-2">
//       <div className="flex justify-between items-center">
//         <h3 className="text-lg font-semibold text-white">{phase.name}</h3>
//         <button 
//           className="text-white hover:text-blue-300"
//           onClick={() => setIsExpanded(!isExpanded)}
//         >
//           {isExpanded ? 'Collapse' : 'Expand'}
//         </button>
//       </div>
//     </div>
//   );
// };

// export default PhaseUI;