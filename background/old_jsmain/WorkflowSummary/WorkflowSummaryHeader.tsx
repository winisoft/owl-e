import React, { useState } from "react";
import IconButton from "../IconButton";
import Icons from "../Icons";
import Icon from "../Icon";
import { GroupType }  from "../../models";

interface WorkflowSummaryHeaderProps {
    workflowId: number;
    name: string;
    onExpand: (workflowId: number, groupType: GroupType) => void;
  }
  
  const WorkflowSummaryHeader: React.FC<WorkflowSummaryHeaderProps> = ({ workflowId, name, onExpand }) => {
    const [isHovered, setIsHovered] = useState<boolean>(false);
    const handleExpand = () => {
        console.log("handle expand from workflow summary header");
        onExpand(workflowId, GroupType.WORKFLOW);
    }
    const handleCloneWorkflow = () => {
        
    }
    const handleJunction = () => {
        // workflow_junctions (
        //     id INTEGER PRIMARY KEY AUTOINCREMENT,
        //     source_step_id INTEGER NOT NULL,
        //     target_workflow_id INTEGER NOT NULL,
        //     trigger_inspector_id INTEGER,
        //     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        //     last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        //     FOREIGN KEY (source_step_id) REFERENCES steps(id),
        //     FOREIGN KEY (target_workflow_id) REFERENCES workflows(id),
        //     FOREIGN KEY (trigger_inspector_id) REFERENCES inspectors(id)
        // );
        
        // prereq_injections (
        //     id INTEGER PRIMARY KEY AUTOINCREMENT,
        //     junction_id INTEGER NOT NULL,
        //     parent_data_point_id INTEGER NOT NULL,
        //     child_data_point_id INTEGER NOT NULL,
        //     FOREIGN KEY (junction_id) REFERENCES workflow_junctions(id),
        //     FOREIGN KEY (parent_data_point_id) REFERENCES data_points(id),
        //     FOREIGN KEY (child_data_point_id) REFERENCES data_points(id)
        // );
    }
    return (
        <div className="flex"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >

        <div className="pr-2">
            <Icon icon={Icons.Workflow} className="w-28 h-28 text-white" />
        </div>
        <h2 className="flex-grow truncate text-xl font-bold text-white">{name}</h2>
            {isHovered && (
                <div className="flex space-x-2">
                    <IconButton icon={Icons.Clone} onClick={handleCloneWorkflow} />
                    <IconButton icon={Icons.Junction} onClick={handleJunction} />
                </div>
            )}
    </div>
    );
  }

export default WorkflowSummaryHeader;