import React from "react";

interface ElementPanelProps {
    elementPanelBtns: React.ReactNode[];
    isVisible: boolean;
}

const ElementPanel: React.FC<ElementPanelProps> = ({ elementPanelBtns, isVisible }) => {
    return (
        <div className="flex space-x-2"> {/* Flex container for buttons */}
        {isVisible && elementPanelBtns.map((btn, index) => (
            <div key={index}> {/* Use a div instead of React.Fragment */}
              {btn}
            </div>
        ))}
    </div>
    );
};

export default ElementPanel;
