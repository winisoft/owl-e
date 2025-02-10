import React from 'react';
import Icons from '../Icons';
import WorkflowSummaryHeader from './WorkflowSummaryHeader';
import WorkflowSummaryStat from './WorkflowSummaryStat';
import { GroupType, WorkflowSummary }  from '../../models';

interface WorkflowSummaryProps {
  summary: WorkflowSummary;
  onExpand: (id: number, groupType: GroupType) => void;
}

const WorkflowSummaryUI: React.FC<WorkflowSummaryProps> = ({ summary, onExpand }) => {
  return (
      <div
      onClick={() => onExpand(summary.id, GroupType.WORKFLOW)} 
        className="workflow shadow-lg rounded-lg p-6"
      >
        <WorkflowSummaryHeader workflowId={summary.id} name={summary.name} onExpand={onExpand} />
        <p className="text-gray-400 mb-4">{summary.description}</p>
        <div className="flex pl-16 pr-16 justify-between">
          <WorkflowSummaryStat 
            icon={Icons.Step} 
            text={`${summary.stepsCount} ${summary.stepsCount == 1 ? 'Step' : 'Steps'}`} 
          />          
          <WorkflowSummaryStat 
            icon={Icons.Data} 
            text={`${summary.dataPointsCount} ${summary.dataPointsCount === 1 ? 'Data Point' : 'Data Points'}`} 
          />
        </div>
      </div>
  );
}



export default WorkflowSummaryUI;
