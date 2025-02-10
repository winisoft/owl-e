// import React from "react";
// import ElementGroup, { GroupType } from "../model/ElementGroup";
// import Icon from "./Icon";
// import Icons from "./Icons";
// import Workflow from "../model/Workflow";
// import { Phase } from "../model/Phase";
// import IconButton from "./IconButton";
// import DataPointsUI from "./DataPoints/DataPointsUI";
// import { NestedElement } from "../model/NestedElement";
// import NestedElementUI from "./NestedElementUI";


// interface ExpandedGroupProps {
//     expandedGroups: ElementGroup[];
// }
  
// const ExpandedGroupsUI: React.FC<ExpandedGroupProps> = ({ expandedGroups }) => {

//     // const tierClass = group.groupType == GroupType.WORKFLOW ?
//     //     "workflow" : `phase-tier-${tier}`;
//     // const nextTier = group.groupType == GroupType.WORKFLOW ? tier : 
//     //     tier === 4 ? 1 : tier + 1;

//     return (
//         groups.map((group: ElementGroup) => (
//             group.children.map((element: NestedElement) => (

//             ))
//         ))
//     );
//     //         <ElementGroupUI
//     //           key={group.children}
//     //           element={element}
//     //           tier={1}
//     //           onExpand={() => console.log(`Expanded ${element.name}`)}
//     //         />
//     //       ))}
//         {groups.map((group: ElementGroup) => (
//             // group.children.map((element: NestedElement) => (
            
//             // ))
//         ))}
//     );
//     //         <ElementGroupUI
//     //           key={group.children}
//     //           element={element}
//     //           tier={1}
//     //           onExpand={() => console.log(`Expanded ${element.name}`)}
//     //         />
//     //       ))}

//     //     <div className="pl-4 pr-4 pt-4 pb-4 workflow rounded-lg shadow-md w-full">
//     //         <div className="flex justify-between items-center mb-4">
//     //             <div className={"pl-2 pr-4"}>
//     //                 <Icon icon={group.groupType == GroupType.WORKFLOW ? Icons.Workflow : Icons.Phase} size={75} className={"w-4 h-4"} />
//     //             </div>
                
//     //             <h1 className="text-3xl flex-grow font-bold text-white">{group.groupType == GroupType.WORKFLOW ? (group as Workflow).name : (group as Phase).name}</h1>
//     //             {group.groupType == GroupType.WORKFLOW ? (
//     //                 <div className="w-auto h-auto flex space-x-4">
//     //                     <IconButton icon={Icons.ExpandItem} onClick={() => {}} />
//     //                     <IconButton icon={Icons.ExpandAll} onClick={() => {}} />
//     //                     <IconButton icon={Icons.PhaseAdd} onClick={() => {}} />
//     //                     <IconButton icon={Icons.StepAdd} onClick={() => {}} />
//     //                     <IconButton icon={Icons.Trash} onClick={() => {}} />
//     //                 </div>
//     //             ) : (
//     //                 <div className="w-auto h-auto flex space-x-4">
//     //                     <IconButton icon={Icons.ExpandItem} onClick={() => {}} />
//     //                     <IconButton icon={Icons.ExpandAll} onClick={() => {}} />
//     //                     <IconButton icon={Icons.PhaseAdd} onClick={() => {}} />
//     //                     <IconButton icon={Icons.StepAdd} onClick={() => {}} />
//     //                     <IconButton icon={Icons.Trash} onClick={() => {}} />
//     //                 </div>
//     //             )}
//     //         </div>
    
//     //     {group.groupType == GroupType.WORKFLOW && (
//     //         <DataPointsUI workflowId={(group as Workflow).id} dataPoints={(group as Workflow).dataPoints}   />
//     //     )}

//     //     <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
//     //         {group.children.map((element: NestedElement) => (
//     //             <NestedElementUI
//     //                 key={element.id}
//     //                 element={element}
//     //                 tier={1}
//     //                 onExpand={() => console.log(`Expanded ${element.name}`)}
//     //             />
//     //         ))}
//     //     </div>
//     // </div>
//     //);
// }

// export default ExpandedGroupsUI;