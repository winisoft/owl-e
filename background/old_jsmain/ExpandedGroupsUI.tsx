import React from "react";
import Icon from "./Icon";
import Icons from "./Icons";
import IconButton from "./IconButton";
import DataPointsUI from "././datapoints/DataPointsUI";
import GroupElementsUI from "./GroupElementsUI";
import { GroupedElementType, GroupSubject, GroupType, Workflow, WorkflowFolder } from "../models";

interface ExpandedGroupsProps {
    expandedGroups: GroupSubject[];
    onNavigateBack: (groupType: GroupType, id: number) => void;
  }
  
  const ExpandedGroupsUI: React.FC<ExpandedGroupsProps> = ({ expandedGroups, onNavigateBack }) => {
    let phaseCount = 0; // Keep track of how many phases we've encountered

    const calculateTier = (groups: GroupSubject[]): number => {
      let tier = 1;
      groups.forEach(group => {
        if (group.elementType === GroupedElementType.PHASE) {
          tier = ((tier - 1) % 4) + 1; // Cycle between 1 and 4 for phases
        }
      });
      return tier;
    };
  
    const currentTier = calculateTier(expandedGroups);
  
    return (
      <div className="">
        {expandedGroups.map( (group: GroupSubject, index: number) => {
          // Determine if it's a workflow or phase
          const isWorkflowOrFolder = group.elementType === GroupedElementType.WORKFLOW || group.elementType === GroupedElementType.FOLDER;
  
          // If it's a phase, increment phaseCount and calculate the tier
          let phaseTier = 1;
          if (group.elementType === GroupedElementType.PHASE) {
            phaseCount++;
            phaseTier = (phaseCount % 4) + 1; // Cycle through tiers 1-4
          }
  
          return (
            <div
              key={index}
              className={`p-4 ${isWorkflowOrFolder ? "workflow" : `phase-tier-${phaseTier}`} rounded-lg flex flex-col justify-between items-start mb-4`}
              onClick={
                () => {}
                //() => onNavigateBack(group.elementType, (group.groupType === GroupType.FOLDER 
                //  ? (group as WorkflowFolder) : group.groupType === GroupType.WORKFLOW ? (group as Workflow) : (group as Phase)).id)
              }
            >
              <div className="group flex items-center justify-between w-full">
                <div className="flex items-center">
                  <Icon
                    icon={group.elementType === GroupedElementType.FOLDER 
                      ? Icons.Folder : group.elementType === GroupedElementType.WORKFLOW 
                      ? Icons.Workflow : Icons.Phase}
                    size={75}
                    className="w-8 h-8 text-white mr-4"
                  />
                  <h1 className="text-3xl font-bold text-white">Group Name</h1> /**//group.name */
                </div>
                <div className="flex space-x-4 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                  <IconButton icon={Icons.ExpandItem} onClick={() => {}} />
                  <IconButton icon={Icons.ExpandAll} onClick={() => {}} />
                  <IconButton icon={Icons.PhaseAdd} onClick={() => {}} />
                  <IconButton icon={Icons.StepAdd} onClick={() => {}} />
                  <IconButton icon={Icons.Trash} onClick={() => {}} />
                </div>
              </div>
              <p className="text-gray-400 text-base mt-2 pl-[4.5rem]"></p>
  
              {group.elementType === GroupedElementType.WORKFLOW && (
                <div className="mt-4 w-full">
                  <DataPointsUI workflowId={(group as Workflow).id} dataPoints={(group as Workflow).dataPoints} />
                </div>
              )}
              <GroupElementsUI tier={currentTier} elements={[]} /> 
              {/* (expandedGroups[expandedGroups.length - 1]).map((group: ElementGroup) => {} */}
            </div>
          );
        })}

        
      </div>
    );
  };
  
  export default ExpandedGroupsUI;
  