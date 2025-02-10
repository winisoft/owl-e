import React, { useState } from 'react';
import FolderOrSummaryListView from '../WorkflowSummary/WorkflowSummaryListView';
import OwleHeader from './OwleHeader';
import ExpandedGroupsUI from '../ExpandedGroupsUI';
import { GroupedElementType, GroupSubject, GroupType, WorkflowFolder, WorkflowSummary } from '../../models';


interface OwleProps {
  elements: GroupSubject[];
  folderBreadcrumb: WorkflowFolder[]; // Add the breadcrumb prop
  onElementClick: (id: number, groupType: GroupType) => void;
  onBreadcrumbClick: (folderIndex: number) => void; // Handle breadcrumb navigation
}

const Owle: React.FC<OwleProps> = ({ elements, folderBreadcrumb, onElementClick, onBreadcrumbClick }) => {
  const [expandedGroups, setExpandedGroups] = useState<GroupSubject[]>([]);

  const pushElementGroup = (group: GroupSubject) => {
    setExpandedGroups((prevGroups) => [...prevGroups, group]);
  };

  const popToGroup = (groupType: GroupType, id: number) => {
    setExpandedGroups((prevGroups) => {
      const targetIndex = prevGroups.findIndex((group) => {
        const referent = group.elementType == GroupedElementType.FOLDER ? (group as WorkflowFolder) : (group as WorkflowSummary);
        return referent.id === id;
      });

      if (targetIndex !== -1) {
        return prevGroups.slice(0, targetIndex + 1);
      }

      return prevGroups;
    });
  };

  const renderFolderBreadcrumb = () => {
    return (
      <div className="folder-breadcrumb text-white mb-4">
        {folderBreadcrumb.map((folder, index) => (
          <span key={folder.id}>
            <a 
              href="#" 
              onClick={() => onBreadcrumbClick(index)}
              className="underline font-bold text-gray-300 hover:text-white"
            >
              {folder.name}
            </a>
            {index < folderBreadcrumb.length - 1 && ' > '}
          </span>
        ))}
      </div>
    );
  };
  const handleCreateWorkflow = async () => {};
  const handleFilterWorkflows = async () => {};
  const handleSettingsClick = async () => {};
  const handleShowTagChips = async () => {};

  return (
    <div className="bg-gray-900 min-h-screen p-2">
      <OwleHeader 
        newWorkflowClick={handleCreateWorkflow}
        filterWorkflowsClick={handleFilterWorkflows}
        settingsClick={handleSettingsClick}
        showTagChips={handleShowTagChips}
      />


      {folderBreadcrumb.length > 0 && renderFolderBreadcrumb()}

      {expandedGroups.length == 0 ? (
        <FolderOrSummaryListView elements={elements} onElementClick={() => {}} /> /**onElementClick */
      ) : (
        <div>
          <ExpandedGroupsUI expandedGroups={expandedGroups} onNavigateBack={popToGroup} />
        </div>
      )}
    </div>
  );
};

export default Owle;
