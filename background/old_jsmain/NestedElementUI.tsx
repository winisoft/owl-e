import React, { useState } from 'react';
import Icons from './Icons';
import Icon from './Icon';
import { GroupSubject, Phase, Step, GroupedElementType } from '../models';

interface NestedElementUIProps {
    element: GroupSubject;
    tier: number; 
    onExpand: () => void;
}

const NestedElementUI: React.FC<NestedElementUIProps> = ({ element, tier, onExpand }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const tierClass = `phase-tier-${tier}`;
  const nextTier = tier === 4 ? 1 : tier + 1; // Calculate the next tier
  // Check if element is a Phase
  if (element.elementType === GroupedElementType.PHASE) {
    const phase = element as Phase;

    return (
      <div
       
        className={`p-4 ${tierClass} rounded-lg shadow-lg`}>
        <div className="flex justify-between items-center mb-4">
            <div className={"pl-2 pr-4"}>
                <Icon icon={Icons.Phase} size={20} className={"w-4 h-4"} />
            </div>
            
          <h2 className="text-xl flex-grow font-semibold text-white">{phase.name}</h2>
          
        </div>

        <p className="text-gray-400 flex-grow mb-2">{phase.description}</p>
        
        {isExpanded && (
          <div className="mt-4">
            <p className="text-gray-500 mb-2">This phase contains {phase.allChildren.length} elements:</p>
            <div className="grid grid-cols-1 gap-4">
              {phase.allChildren.map((child: GroupSubject) => (
                <NestedElementUI 
                  key={child.id} 
                  element={child} 
                  tier={nextTier}
                  onExpand={onExpand} 
                />
              ))}
            </div>
          </div>
        )}
      </div>
    );
  }

  // Check if element is a Step
  if (element.elementType === GroupedElementType.STEP) {
    const step = element as Step;

    return (
      <div className="p-4 bg-gray-700 rounded-lg shadow-md">
        <div className="flex justify-between items-center mb-4">
          <h3 className="text-xl font-semibold text-white">{step.name}</h3>
          <button 
            onClick={onExpand} 
            className="text-white text-lg bg-green-500 px-2 py-1 rounded-md">
            View Step
          </button>
        </div>

        <p className="text-gray-400 mb-2">Tool: {step.tool.name}</p>

        {step.isWorkflowHead && (
          <p className="text-sm text-green-300">This is the workflow head</p>
        )}
      </div>
    );
  }

  return null; // Should not reach here
};

export default NestedElementUI;
