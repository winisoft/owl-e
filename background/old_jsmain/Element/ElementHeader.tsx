import React from "react";
import Icon from "../Icon";

interface ElementHeaderProps {
    icon: string;
    text: React.ReactNode;
    panel: React.ReactNode;
}
  
const ElementHeader: React.FC<ElementHeaderProps> = ({ icon, text, panel }) => {
    return (
        <div className="w-auto flex justify-between items-center"> 
            <Icon icon={icon} className={"w-10 h-10"} />
            {text}
            {panel}
        </div>
    );
}

export default ElementHeader;