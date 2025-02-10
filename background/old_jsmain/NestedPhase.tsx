import React, { useState } from "react";
import Icons from "./Icons";
import Icon from "./Icon";
import NestedStep from "./NestedStep";
import { GroupSubject, OwleGroup, Phase, Step, GroupedElementType } from "../models";


interface NestedPhaseProps {
    phase: Phase;
    tierClass: string; 
    onExpand: () => void;
}

const NestedPhase: React.FC<NestedPhaseProps> = ({ phase, tierClass, onExpand }) => {
    return (
        <div className={`p-4 ${tierClass} rounded-lg shadow-lg h-full`}>
            <div className="flex justify-between items-center mb-4">
                <div className="pl-2 pr-4">
                    <Icon icon={Icons.Phase} size={20} className="w-6 h-6" />
                </div>
                <h2 className="text-xl flex-grow font-semibold text-white">{phase.name}</h2>
            </div>
            <p className="text-gray-400 flex-grow mb-2">{phase.description}</p>
            
            {/* Scrollable child elements */}
            <div className="overflow-y-auto max-h-112"> {/* Adjust max-h-64 as needed */}
                {(phase as OwleGroup).allChildren.map((child: GroupSubject) => (
                    child.elementType === GroupedElementType.PHASE ? (
                        <NestedPhase key={child.id} phase={(child as Phase)} tierClass={tierClass} onExpand={onExpand} />
                    ) : (
                        <NestedStep key={(child as Step).id} step={(child as Step)} onExpand={onExpand} />
                    )
                ))}
            </div>
        </div>
    );
};

export default NestedPhase;
