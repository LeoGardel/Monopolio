package monopoly.GUI;

import monopoly.Monopoly;
import monopoly.RightPanelGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ButtonForm extends Button
{
	public static final int defaultButtonWidth = 128;
	public static final int defaultButtonHeight = 32;
	public static final int defaultGUIWidth = (int) (Monopoly.applicationInitialWidth * Monopoly.splitFactor);
	public static final int defaultGUIHeight = Monopoly.applicationInitialHeight;
	public static NinePatch nineButton;
	public static NinePatch nineButtonHovered;
	public static NinePatch nineButtonClicked;
	public static ButtonForm clickedButton;
	public static ButtonForm hoveredButton;
	
	public int totalRows;
	public int myRow;
	public int totalCols;
	public int myCol;
	
	public int myDefaultWidth;
	public int myDefaultHeight;
	
	
	/*
	 *Construtor que inicializa o botão.
	 *Parâmetros:
	 *	text - Texto que vai ser escrito na label, dentro do botão
	 *	myRow - linha em que o botão vai ficar, em uma subdivisão de "totalRows" linhas
	 *	totalRows - total de linhas que vão ser consideradas na subdivisão do eixo vertical
	 *	myCol - coluna em que o botão vai ficar, em uma subdivisão de "totalCols" linhas
	 *	totalCols - total de colunas que vão ser consideradas na subdivisão do eixo vertical
	 *	width - largura sugerida para o botão
	 *	height - altura sugerida para o botão
	 */
	public ButtonForm(String text, int myRow, int totalRows, int myCol, int totalCols, int width, int height)
	{
		super(new ButtonStyle());
		
		this.myDefaultWidth = width;
		this.myDefaultHeight = height;
		this.myRow = myRow;
		this.totalRows = totalRows;
		this.myCol = myCol;
		this.totalCols = totalCols;
		
		setUpVariables(text);
	}
	
	/*
	 *Construtor que inicializa o botão com as dimensões (width e height) padrão
	 *	text - Texto que vai ser escrito na label, dentro do botão
	 *	myRow - linha em que o botão vai ficar, em uma subdivisão de "totalRows" linhas
	 *	totalRows - total de linhas que vão ser consideradas na subdivisão do eixo vertical
	 *	myCol - coluna em que o botão vai ficar, em uma subdivisão de "totalCols" linhas
	 *	totalCols - total de colunas que vão ser consideradas na subdivisão do eixo vertical
	 */
	public ButtonForm(String text, int myRow, int totalRows, int myCol, int totalCols)
	{
		super(new ButtonStyle());
		
		this.myDefaultWidth = ButtonForm.defaultButtonWidth;
		this.myDefaultHeight = ButtonForm.defaultButtonHeight;
		this.myRow = myRow;
		this.totalRows = totalRows;
		this.myCol = myCol;
		this.totalCols = totalCols;
		
		setUpVariables(text);
	}
	
	/*
	 *Função que inicializa as variáveis do botão.
	 */
	private void setUpVariables(String text) {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont();
		labelStyle.fontColor = new Color(0, 0, 0, 1);
		Label label = new Label(text, labelStyle);
		this.add(label);
		
		super.style.up = ButtonForm.nineButton;

		float newXSize = (Gdx.graphics.getWidth()*(1 - Monopoly.splitFactor));
		float newYSize = Gdx.graphics.getHeight();
		resize(newXSize, newYSize);
	}
	
	/*
	 *Função que aplica o efeito do botão.
	 */
	public void effect(){
		
	}
	
	/*
	 *Função invocada quando o botão é pressionado.
	 */
	public void touchDown() {
		clickedButton = this;
		this.style.up = nineButtonClicked;
	}
	
	/*
	 *Função invocada quando o botão é despressionado.
	 */
	public void touchUp() {
		clickedButton = null;
		this.style.up = nineButtonHovered;
		effect();
	}
	
	/*
	 *Função que marca o botão como sob o cursor.
	 */
	public void setAsHovered() {
		if (hoveredButton != null && !(hoveredButton.equals(this))) {
			clearHovered();
		}
		hoveredButton = this;
		this.style.up = nineButtonHovered;
	}
	
	/*
	 *Função que desmarca o botão que estava sob o cursor, caso haja.
	 */
	public static void clearHovered()
	{
		if (hoveredButton != null) {
			hoveredButton.style.up = nineButton;
			hoveredButton = null;
		}
	}
	
	/*
	 *Função que "desclica" o botão que está clicado, caso haja.
	 */
	public static void clearClicked()
	{
		if (clickedButton != null) {
			clickedButton.style.up = nineButton;
			clickedButton = null;
		}
	}
	
	/*
	 *Função que carrega as imagens dos botões em seus diferentes estilos possíveis.
	 */
	public static void loadNinesForButtons()
	{
		nineButton = new NinePatch(new Texture(Gdx.files.internal("resources/background-button.png")), 0, 0, 0, 0);
		nineButtonHovered = new NinePatch(new Texture(Gdx.files.internal("resources/background-buttonHovered.png")), 0, 0, 0, 0);
		nineButtonClicked = new NinePatch(new Texture(Gdx.files.internal("resources/background-buttonClicked.png")), 0, 0, 0, 0);
	}
	
	/*
	 *Função que reescala o botão, de acordo com as reescalas na janela do jogo.
	 */
	public void resize(float x, float y)
	{
		setWidthAndHeight(x, y);
		
		float xPartitionSize = x / totalCols;
		float yPartitionSize = y / totalRows;
		
		float centralizedX = ((myCol - 1) + 0.5f ) * xPartitionSize;
		float centralizedY = y - ((myRow - 1) + 0.5f ) * yPartitionSize;
		
		centralizeAt(centralizedX, centralizedY);
	}
	
	/*
	 *Função que centraliza o botão na posição passada nos parâmetros.
	 */
	private void centralizeAt(float x, float y) {
		this.x = x - (this.width / 2);
		this.y = y - (this.height / 2);
	}
	
	/*
	 *Função que recalcula o tamanho do botão baseado em seu enquandramento.
	 */
	private void setWidthAndHeight(float x, float y ) {
		if ((x / this.totalCols) < myDefaultWidth + RightPanelGUI.elementsMinPadding) {
			float newWidth = (x / this.totalCols) - RightPanelGUI.elementsMinPadding;
			if (newWidth >= RightPanelGUI.elementsMinSize) {
				this.width = newWidth;
			}
			else {
				this.width = RightPanelGUI.elementsMinSize;
			}
		}
		else {
			this.width = myDefaultWidth;
		}
		if ((y / this.totalRows) < myDefaultHeight + RightPanelGUI.elementsMinPadding) {
			float newHeight = (y / this.totalRows) - RightPanelGUI.elementsMinPadding;
			if (newHeight >= RightPanelGUI.elementsMinSize) {
				this.height = newHeight;
			}
			else {
				this.height = RightPanelGUI.elementsMinSize;
			}
		}
		else {
			this.height = myDefaultHeight;
		}
	}
}
