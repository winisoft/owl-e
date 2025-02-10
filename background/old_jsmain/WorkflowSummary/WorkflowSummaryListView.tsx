import React from "react";
import FolderUI from "../Folder/Folder";
import WorkflowSummaryUI from "./WorkflowSummaryUI";
import { GroupSubject, WorkflowFolder, WorkflowSummary, GroupedElementType } from "../../models";


interface WorkflowsViewProps {
  elements: GroupSubject[];
  onElementClick: (id: number, groupType: GroupedElementType) => void;
}

const FolderOrSummaryListView: React.FC<WorkflowsViewProps> = ({ elements, onElementClick }) => {
  return (
    <div className="grid grid-cols-2 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {elements.map((element: GroupSubject) => (
        element.elementType === GroupedElementType.FOLDER ?  
        <FolderUI
          key={element.id}
          folder={(element) as WorkflowFolder} 
          onElementClick={(id, groupType) => onElementClick(id, groupType)} 
        /> : <WorkflowSummaryUI 
          key={element.id}
          summary={element as WorkflowSummary} 
          onExpand={(id: number, groupType: any) => onElementClick(id, element.elementType)} 
        />
      ))};
    </div>
  )
};

export default FolderOrSummaryListView;
