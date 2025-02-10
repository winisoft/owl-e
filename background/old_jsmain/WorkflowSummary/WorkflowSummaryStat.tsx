import React from "react";
import IconButton from "../IconButton";
import Icons from "../Icons";
import Icon from "../Icon";

interface WorkflowSummaryStatProps {
    icon: string;
    text: string;
  }
  
  const WorkflowSummaryStat: React.FC<WorkflowSummaryStatProps> = ({ icon, text }) => {
    return (
        <div className="flex items-center">
            <div className="pl-4 pr-4">
            <Icon icon={icon} />
            </div>
            <span className="text-lg h-10 w-20 text-gray-400">{text}</span>
        </div>
    );
  }

export default WorkflowSummaryStat;