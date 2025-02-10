import React from 'react';
import FolderStat from './FolderStat';
import Icons from '../Icons';
import FolderHeader from '../Folder/FolderHeader';
import { WorkflowFolder, GroupedElementType } from '../../models';

interface FolderProps {
  folder: WorkflowFolder;
  onElementClick: (id: number, groupType: GroupedElementType) => void;
}

const FolderUI: React.FC<FolderProps> = ({ folder, onElementClick }) => {
  return (
      <div
        onClick={() => onElementClick(folder.id, GroupedElementType.FOLDER)} 
        className="folder shadow-lg rounded-lg p-6"
      >
        <FolderHeader 
            id={folder.id} 
            name={folder.name} 
        />
        <p className="text-gray-400 mb-4">{folder.description}</p>
        <div className="flex pl-16 pr-16 justify-between">
          {folder.subFolders.length === 0 ? (
            <div>
              <FolderStat 
                icon={Icons.Workflow} 
                text={`${folder.childWorkflows} ${folder.childWorkflows.length == 1 ? 'Workflow' : 'Workflows'}`} 
              />  
            </div>
          ) : (
            <div>
              <FolderStat 
                icon={Icons.Workflow} 
                text={`${folder.childWorkflows} ${folder.childWorkflows.length == 1 ? 'Workflow' : 'Workflows'}`} 
              />
              <p className="text-xl font-bold text-white">in</p>
              <FolderStat 
                icon={Icons.Folder} 
                text={`${folder.subFolders.length} ${folder.subFolders.length == 1 ? 'Child Folder' : 'Child Folders'}`} 
              />
              </div>
            
          )}
          
        </div>
      </div>
  );
}



export default FolderUI;
