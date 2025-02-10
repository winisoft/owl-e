import React, { useState } from 'react';
import Icons from '../Icons';
import ElementHeader from '../Element/ElementHeader';
import ElementPanel from '../Element/ElementPanel';

interface DataPointProvisionCardProps {
    phaseId?: number;
    phaseName?: string;
    stepId: number;
    stepName: string;
    tool: string;
    param: string;
}

const DataPointProvisionCard: React.FC<DataPointProvisionCardProps> = ({ 
    phaseId = null,
    phaseName = null,
    stepId,
    stepName,
    tool,
    param
 }) => {
    return (
        <div>
            {phaseId && phaseName && (
                <ElementHeader 
                icon={Icons.Phase}//icon={<div className="pr-4 pb-4"><PhaseIcon className="w-10 h-10"/></div>}
                    text={<h2 className="flex-grow truncate text-xl font-bold text-white">{phaseName}</h2>}
                    panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
                />
            )}
            <ElementHeader 
                icon={Icons.Step}//icon={<div className="pr-4 pb-4"><StepIcon className="w-10 h-10"/></div>}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{stepName}</h2>}
                panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
            />
            <ElementHeader 
                icon={Icons.Function}//icon={<div className="pr-4 pb-4"><FunctionIcon className="w-10 h-10"/></div>}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{tool}</h2>}
                panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
            />            
            <ElementHeader 
                icon={Icons.Param}//icon={<div className="pr-4 pb-4"><ParamIcon className="w-10 h-10"/></div>}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{param}</h2>}
                panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
            />
        </div>
    );
  }
export default DataPointProvisionCard;