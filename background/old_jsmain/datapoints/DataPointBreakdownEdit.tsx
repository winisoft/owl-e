import React, { useState } from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import ElementHeader from '../Element/ElementHeader';
import ElementPanel from '../Element/ElementPanel';
import { NewInquisition, NewProvision, NewRevision } from '../../models';

interface DataPointBreakdownEditProps {
    provisions: NewProvision[];
    revisions: NewRevision[];
    inquisitions: NewInquisition[];
    setProvisions: (provisions: NewProvision[]) => void;
    setRevisions: (revisions: NewRevision[]) => void;
    setInquisitions: (inquisitions: NewInquisition[]) => void;
}

const DataPointBreakdownEdit: React.FC<DataPointBreakdownEditProps> = ({ 
    provisions,
    revisions,
    inquisitions,
    setProvisions,
    setRevisions,
    setInquisitions,
 }) => {
    const [isProvisionsHovered, setIsProvisionsHovered] = useState<boolean>(false);
    const [isRevisionsHovered, setIsRevisionsHovered] = useState<boolean>(false);
    const [isInquisitionsHovered, setIsInquisitionsHovered] = useState<boolean>(false);
    const [isProvisionsExpanded, setIsProvisionsExpanded] = useState<boolean>(false);
    const [isRevisionsExpanded, setIsRevisionsExpanded] = useState<boolean>(false);
    const [isInquisitionsExpanded, setIsInquisitionsExpanded] = useState<boolean>(false);
    return (
        <div>
            <ElementHeader 
            //<div className="pr-4 pb-4"><DependencyIcon className="w-10 h-10"/></div>
                icon={Icons.Provisions}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">Provided to {provisions.length} ${provisions.length == 1 ? 'Step' : 'Steps'}</h2>}
                panel={
                <ElementPanel 
                    elementPanelBtns={
                    [
                        <IconButton key="add" icon={Icons.AddItem} onClick={() => {}} />,
                        <IconButton key="toggle-expanded" icon={isProvisionsExpanded ? Icons.CollapseSection : Icons.ExpandSection} onClick={() => {}} />,
                    ]} 
                    isVisible={isProvisionsHovered}
                />}
            />
            <ElementHeader 
                icon={Icons.Revisions}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">Assigned by {revisions.length} ${revisions.length == 1 ? 'Step' : 'Steps'}</h2>}
                panel={
                <ElementPanel 
                    elementPanelBtns={
                    [
                        <IconButton key="add" icon={Icons.AddItem} onClick={() => {}} />,
                        <IconButton key="toggle-expanded" icon={isProvisionsExpanded ? "icon-settings" : "icon-settings"} onClick={() => {}} />,
                    ]} 
                    isVisible={isRevisionsHovered}
                />}
            />
            <ElementHeader 
                icon={Icons.Inspectors}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">Redirects in {inquisitions.length} ${inquisitions.length == 1 ? 'Case' : 'Cases'}</h2>}
                panel={
                <ElementPanel 
                    elementPanelBtns={
                    [
                        <IconButton key="add" icon={Icons.AddItem} onClick={() => {}} />,
                        <IconButton key="toggle-expanded" icon={isProvisionsExpanded ? "icon-settings" : "icon-settings"} onClick={() => {}} />,
                    ]} 
                    isVisible={isInquisitionsHovered}
                />}
            />
        </div>
    );
  }
export default DataPointBreakdownEdit;