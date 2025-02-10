import React, { useState } from 'react';
import Icons from '../Icons';
import ElementHeader from '../Element/ElementHeader';
import ElementPanel from '../Element/ElementPanel';
import Icon from '../Icon';

interface DataPointInquisitionCardProps {
    phaseId?: number;
    phaseName?: string;
    stepId: number;
    stepName: string;
    className: string;
    onMetStepId: number;
    onMetStepName: string;
}

const DataPointInquisitionCard: React.FC<DataPointInquisitionCardProps> = ({ 
    phaseId = null,
    phaseName = null,
    stepId,
    stepName,
    className,
    onMetStepId,
    onMetStepName
 }) => {
    return (
        <div>
            {phaseId && phaseName && (
                <ElementHeader 
                    icon={Icons.Phase}
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
                icon={Icons.Inspector}//icon={<div className="pr-4 pb-4"><InspectorIcon className="w-10 h-10"/></div>}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{className}</h2>}
                panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
            />            
            <ElementHeader 
                icon={Icons.StepNext}//icon={<div className="pr-4 pb-4"><StepNextIcon className="w-10 h-10"/></div>}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{onMetStepName}</h2>}
                panel={ <ElementPanel elementPanelBtns={[]} isVisible={false}/> }
            />
        </div>
    );
  }
export default DataPointInquisitionCard;