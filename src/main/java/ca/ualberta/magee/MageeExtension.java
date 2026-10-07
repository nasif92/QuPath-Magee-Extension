package ca.ualberta.magee;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;

import qupath.lib.common.Version;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.extensions.QuPathExtension;
import qupath.lib.gui.tools.IconFactory;
import qupath.lib.gui.tools.IconFactory.PathIcons;

/**
 * Adds the Magee tools to an unmodified QuPath install:
 * a "Magee" menu under Extensions, plus the same three toolbar buttons
 * the forked build had (Export annotations, Import detections, Magee calculator).
 */
public class MageeExtension implements QuPathExtension {

    private static final String NAME = "Magee Tools";
    private static final String DESCRIPTION =
            "Magee Equation Calculator, annotation export and detection import.";
    private static final Version QUPATH_VERSION = Version.parse("0.7.0");

    private boolean isInstalled = false;

    @Override
    public void installExtension(QuPathGUI qupath) {
        if (isInstalled)
            return;
        isInstalled = true;
        addMenuItems(qupath);
        addToolbarButtons(qupath);
    }

    private void addMenuItems(QuPathGUI qupath) {
        Menu menu = qupath.getMenu("Extensions>" + NAME, true);

        MenuItem export = new MenuItem("Export annotations");
        export.setOnAction(e -> AnnotationExportTool.exportCurrentImageAnnotations(qupath));

        MenuItem importDet = new MenuItem("Import detections");
        importDet.setOnAction(e -> DetectionImportTool.importCurrentImageDetections(qupath));

        MenuItem magee = new MenuItem("Magee equation calculator");
        magee.setOnAction(e -> MageeTools.showMageeCalculator(qupath));

        menu.getItems().addAll(export, importDet, magee);
    }

    private void addToolbarButtons(QuPathGUI qupath) {
        var toolbar = qupath.getToolBar();
        if (toolbar == null)
            return;

        Button btnExport = toolbarButton("Export annotations", PathIcons.ARROW_END_TOOL);
        btnExport.setOnAction(e -> AnnotationExportTool.exportCurrentImageAnnotations(qupath));

        Button btnImport = toolbarButton("Import detections", PathIcons.DOWNLOAD);
        btnImport.setOnAction(e -> DetectionImportTool.importCurrentImageDetections(qupath));

        Button btnMagee = toolbarButton("Magee equation calculator", PathIcons.MEASURE);
        btnMagee.setOnAction(e -> MageeTools.showMageeCalculator(qupath));

        var items = toolbar.getItems();
        int insertAt = indexAfterSelectionModeGroup(items);
        if (insertAt < 0) {
            // Unexpected toolbar layout (e.g. a different QuPath version): fall back to the far right
            items.addAll(new Separator(), btnExport, btnImport, btnMagee);
            return;
        }
        // Lands as: [drawing tools] | [selection mode] | [our buttons] | [brightness/contrast] ...
        items.addAll(insertAt, List.of(btnExport, btnImport, btnMagee, new Separator()));
    }

    /**
     * QuPath's toolbar is laid out as:
     *   [analysis pane] | [drawing tools] | [selection mode] | [brightness/contrast] | ...
     * so the index just after the third separator is where our group goes.
     * Returns -1 if the layout isn't recognised.
     */
    private static int indexAfterSelectionModeGroup(List<Node> items) {
        int separators = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Separator && ++separators == 3)
                return i + 1;
        }
        return -1;
    }

    private static Button toolbarButton(String tooltip, PathIcons icon) {
        Button btn = new Button();
        btn.setTooltip(new Tooltip(tooltip));
        btn.setGraphic(IconFactory.createNode(
                QuPathGUI.TOOLBAR_ICON_SIZE, QuPathGUI.TOOLBAR_ICON_SIZE, icon));
        return btn;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Version getQuPathVersion() {
        return QUPATH_VERSION;
    }
}
