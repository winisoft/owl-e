import React from "react";
import NestedPhase from './NestedPhase';
import NestedStep from './NestedStep';
import { GroupType, Phase, Step, GroupSubject } from "../models";


interface GroupElementsProps {
    tier: number;
    elements: GroupSubject[];
}
  
const GroupElementsUI: React.FC<GroupElementsProps> = ({ tier, elements }) => {
    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 w-full auto-fill">
            {elements.map((element: GroupSubject) => (
                (element.parent?.groupType === GroupType.PHASE) ? (
                    <NestedPhase 
                        phase={(element as Phase)} 
                        tierClass={`phase-tier-${tier}`} 
                        onExpand={() => {}} 
                    />
                ) : (
                    <NestedStep 
                        step={(element as Step)} 
                        onExpand={() => {}} 
                    />
                )
            ))}
        </div>
    );
};

export default GroupElementsUI;
