import React, { useState } from 'react';

import Icon from './Icon';
import Icons from './Icons';
import IconButton from './IconButton';
import DataPointsUI from '././datapoints/DataPointsUI';
import NestedElementUI from './NestedElementUI';
import { GroupSubject, NewInquisition, NewProvision, NewRevision, Workflow } from '../models';

interface WorkflowProps {
  workflow: Workflow;
}

const WorkflowUI: React.FC<WorkflowProps> = ({ workflow }) => {
  const [showDataPoints, setShowDataPoints] = useState<boolean>(false);
  const [newItemEditing, setNewItemEditing] = useState<boolean>(false);

  const toggleExpandedClick = () => {
    setShowDataPoints(!showDataPoints);
  };

  const handleCreateDataPoint = (
    name: string,
    type: string,
    description: string,
    readOnly: boolean,
    value: string,
    provisions: NewProvision[],
    revisions: NewRevision[],
    inquisitions: NewInquisition[]
  ) => {
    // Logic for creating a new data point
  };

  const handleQueryTextChange = (queryText: string) => {
    // Logic for handling search/filter query change
  };

  return (
    <div className="p-6 workflow rounded-lg shadow-md w-full">
      <div className="flex justify-between items-center mb-4">
            <div className={"pl-2 pr-4"}>
                <Icon icon={Icons.Workflow} size={75} className={"w-4 h-4"} />
            </div>
            
          <h1 className="text-4xl flex-grow font-bold text-white">{workflow.name}</h1>
          <div className="w-auto h-auto flex space-x-4">
            <IconButton icon={Icons.ExpandItem} onClick={toggleExpandedClick} />
            <IconButton icon={Icons.ExpandAll} onClick={() => {}} />
            <IconButton icon={Icons.PhaseAdd} onClick={() => {}} />
            <IconButton icon={Icons.StepAdd} onClick={() => {}} />
            <IconButton icon={Icons.Trash} onClick={() => {}} />
          </div>
        </div>

  <DataPointsUI workflowId={workflow.id} dataPoints={workflow.dataPoints}   />
  <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        {workflow.allChildren.map((element: GroupSubject) => (
          <NestedElementUI
            key={element.id}
            element={element}
            tier={1}
            onExpand={() => console.log(`Expanded ${element.name}`)}
          />
        ))}
      </div>
  </div>
  );
};

export default WorkflowUI;
