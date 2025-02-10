import React from "react";
import IconButton from "../IconButton";

interface ElementPanelButtonProps {
    iconButton: React.ReactNode;
    callback: () => void;
}
  
const ElementPanelButton: React.FC<ElementPanelButtonProps> = ({ iconButton, callback }) => {
    const handleCallback = () => {
        console.log("handleCallback from ElementPanelButton")
    }
    return (
        <div className="w-auto h-auto" onClick={handleCallback}> 
            {iconButton}
        </div>
    );
}

export default ElementPanelButton;