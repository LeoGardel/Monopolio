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
	 *Construtor que inicializa o bot�o.
	 *Par�metros:
	 *	text - Texto que vai ser escrito na label, dentro do bot�o
	 *	myRow - linha em que o bot�o vai ficar, em uma subdivis�o de "totalRows" linhas
	 *	totalRows - total de linhas que v�o ser consideradas na subdivis�o do eixo vertical
	 *	myCol - coluna em que o bot�o vai ficar, em uma subdivis�o de "totalCols" linhas
	 *	totalCols - total de colunas que v�o ser consideradas na subdivis�o do eixo vertical
	 *	width - largura sugerida para o bot�o
	 *	height - altura sugerida para o bot�o
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
	 *Construtor que inicializa o bot�o com as dimens�es (width e height) padr�o
	 *	text - Texto que vai ser escrito na label, dentro do bot�o
	 *	myRow - linha em que o bot�o vai ficar, em uma subdivis�o de "totalRows" linhas
	 *	totalRows - total de linhas que v�o ser consideradas na subdivis�o do eixo vertical
	 *	myCol - coluna em que o bot�o vai ficar, em uma subdivis�o de "totalCols" linhas
	 *	totalCols - total de colunas que v�o ser consideradas na subdivis�o do eixo vertical
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
	 *Fun��o que inicializa as vari�veis do bot�o.
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
	 *Fun��o que aplica o efeito do bot�o.
	 */
	public void effect(){
		
	}
	
	/*
	 *Fun��o invocada quando o bot�o � pressionado.
	 */
	public void touchDown() {
		clickedButton = this;
		this.style.up = nineButtonClicked;
	}
	
	/*
	 *Fun��o invocada quando o bot�o � despressionado.
	 */
	public void touchUp() {
		clickedButton = null;
		this.style.up = nineButtonHovered;
		effect();
	}
	
	/*
	 *Fun��o que marca o bot�o como sob o cursor.
	 */
	public void setAsHovered() {
		if (hoveredButton != null && !(hoveredButton.equals(this))) {
			clearHovered();
		}
		hoveredButton = this;
		this.style.up = nineButtonHovered;
	}
	
	/*
	 *Fun��o que desmarca o bot�o que estava sob o cursor, caso haja.
	 */
	public static void clearHovered()
	{
		if (hoveredButton != null) {
			hoveredButton.style.up = nineButton;
			hoveredButton = null;
		}
	}
	
	/*
	 *Fun��o que "desclica" o bot�o que est� clicado, caso haja.
	 */
	public static void clearClicked()
	{
		if (clickedButton != null) {
			clickedButton.style.up = nineButton;
			clickedButton = null;
		}
	}
	
	/*
	 *Fun��o que carrega as imagens dos bot�es em seus diferentes estilos poss�veis.
	 */
	public static void loadNinesForButtons()
	{
		nineButton = new NinePatch(new Texture(Gdx.files.internal("resources/background-button.png")), 0, 0, 0, 0);
		nineButtonHovered = new NinePatch(new Texture(Gdx.files.internal("resources/background-buttonHovered.png")), 0, 0, 0, 0);
		nineButtonClicked = new NinePatch(new Texture(Gdx.files.internal("resources/background-buttonClicked.png")), 0, 0, 0, 0);
	}
	
	/*
	 *Fun��o que reescala o bot�o, de acordo com as reescalas na janela do jogo.
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
	 *Fun��o que centraliza o bot�o na posi��o passada nos par�metros.
	 */
	private void centralizeAt(float x, float y) {
		this.x = x - (this.width / 2);
		this.y = y - (this.height / 2);
	}
	
	/*
	 *Fun��o que recalcula o tamanho do bot�o baseado em seu enquandramento.
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
