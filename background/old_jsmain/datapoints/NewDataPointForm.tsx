// import React from 'react';
// import WorkflowSummary from '../../model/WorkflowSummary';
// import Icons from '../Icons';
// import IconButton from '../IconButton';
// import { NewDataPoint } from '../../model/DataPoint';
// import WorkflowSummaryHeader from '../WorkflowSummary/WorkflowSummaryHeader';

// interface NewDataPointFormProps {
//     editingDataPoint: NewDataPoint;
//     onDiscardClick: () => void;
//     onSubmitClick: (edited: NewDataPoint) => void;
// }

// const NewDataPointForm: React.FC<NewDataPointFormProps> = ({ editingDataPoint, onDiscardClick, onSubmitClick }) => {
//   return (
//       <div
//         className="bg-gray-800 shadow-lg rounded-lg p-6"
//       >
//         <WorkflowSummaryHeader 
//             workflowId={summary.id} name={summary.name} onExpand={onExpand} />
//         <p className="text-gray-400 mb-4">{summary.description}</p>
//         <div className="flex pl-16 pr-16 justify-between">
//           <WorkflowSummaryStat 
//             icon={<StepIcon />} 
//             text={`${summary.steps} ${summary.steps == 1 ? 'Step' : 'Steps'}`} 
//           />          
//           <WorkflowSummaryStat 
//             icon={<DataIcon />} 
//             text={`${summary.dataPoints} ${summary.dataPoints == 1 ? 'Data Point' : 'Data Points'}`} 
//           />
//         </div>
//       </div>
//   );
// }



// export default NewDataPointForm;